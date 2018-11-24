package com.ford.wechat.batch.item.reader.common;


import com.ford.wechat.batch.model.dms.factory.FactoryVin;
import com.ford.wechat.entity.factory.WeFactoryForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxUtils;
import org.springframework.batch.item.xml.stax.DefaultFragmentEventReader;
import org.springframework.batch.item.xml.stax.FragmentEventReader;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by xuwenfeng on 16/7/30.
 */
public class DataFileStaxEventItemReader<T> extends StaxEventItemReader<T> {



    private static final Log logger = LogFactory.getLog(DataFileStaxEventItemReader.class);

    private FragmentEventReader fragmentReader;

    private XMLEventReader eventReader;

    private Unmarshaller unmarshaller;

    private Resource resource;

    private InputStream inputStream;

    private List<QName> fragmentRootElementNames;

    private boolean noInput;

    private boolean strict = true;





    public DataFileStaxEventItemReader() {
        this.setName(ClassUtils.getShortName(DataFileStaxEventItemReader.class));
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void setFragmentRootElementName(String fragmentRootElementName) {
        this.setFragmentRootElementNames(new String[]{fragmentRootElementName});
    }

    public void setFragmentRootElementNames(String[] fragmentRootElementNames) {
        this.fragmentRootElementNames = new ArrayList();
        String[] arr$ = fragmentRootElementNames;
        int len$ = fragmentRootElementNames.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String fragmentRootElementName = arr$[i$];
            this.fragmentRootElementNames.add(this.parseFragmentRootElementName(fragmentRootElementName));
        }

    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.unmarshaller, "The Unmarshaller must not be null.");
        Assert.notEmpty(this.fragmentRootElementNames, "The FragmentRootElementNames must not be empty");
        Iterator i$ = this.fragmentRootElementNames.iterator();

        while(i$.hasNext()) {
            QName fragmentRootElementName = (QName)i$.next();
            Assert.hasText(fragmentRootElementName.getLocalPart(), "The FragmentRootElementNames must not contain empty elements");
        }

    }

    protected boolean moveCursorToNextFragment(XMLEventReader reader) throws NonTransientResourceException {
        try {
            while(true) {
                if(reader.peek() != null && !reader.peek().isStartElement()) {
                    reader.nextEvent();
                } else {
                    if(reader.peek() == null) {
                        return false;
                    }

                    QName e = ((StartElement)reader.peek()).getName();
                    if(this.isFragmentRootElementName(e)) {
                        return true;
                    }

                    reader.nextEvent();
                }
            }
        } catch (XMLStreamException var3) {
            throw new NonTransientResourceException("Error while reading from event reader", var3);
        }
    }

    protected void doClose() throws Exception {
        try {
            if(this.fragmentReader != null) {
                this.fragmentReader.close();
            }

            if(this.inputStream != null) {
                this.inputStream.close();
            }
        } finally {
            this.fragmentReader = null;
            this.inputStream = null;
        }

    }

    protected void doOpen() throws Exception {
        Assert.notNull(this.resource, "The Resource must not be null.");
        this.noInput = true;
        if(!this.resource.exists()) {
            if(this.strict) {
                throw new IllegalStateException("Input resource must exist (reader is in \'strict\' mode)");
            } else {
                logger.warn("Input resource does not exist " + this.resource.getDescription());
            }
        } else if(!this.resource.isReadable()) {
            if(this.strict) {
                throw new IllegalStateException("Input resource must be readable (reader is in \'strict\' mode)");
            } else {
                logger.warn("Input resource is not readable " + this.resource.getDescription());
            }
        } else {
            this.inputStream = this.resource.getInputStream();
            this.eventReader = XMLInputFactory.newInstance().createXMLEventReader(this.inputStream);
            this.fragmentReader = new DefaultFragmentEventReader(this.eventReader);
            this.noInput = false;
        }
    }

    protected T doRead() throws Exception {
        if(this.noInput) {
            return null;
        } else {
            Object item = null;
            boolean success = false;

            try {
                success = this.moveCursorToNextFragment(this.fragmentReader);
            } catch (NonTransientResourceException var8) {
                this.noInput = true;
                throw var8;
            }

            if(success) {
                this.fragmentReader.markStartFragment();

                try {
                    Source source=StaxUtils.getSource(this.fragmentReader);
                    Object mappedFragment = this.unmarshaller.unmarshal(source);
                    item = mappedFragment;
                } finally {
                    this.fragmentReader.markFragmentProcessed();
                }
            }


            if(item instanceof FactoryVin){
                FactoryVin t=(FactoryVin) item;
                t.setFileName(resource.getFilename());
                item=t;
            }



            return (T)item;
        }
    }


    protected void jumpToItem(int itemIndex) throws Exception {
        for(int i = 0; i < itemIndex; ++i) {
            try {
                QName e = this.readToStartFragment();
                this.readToEndFragment(e);
            } catch (NoSuchElementException var4) {
                if(itemIndex == i + 1) {
                    return;
                }

                throw var4;
            }
        }

    }

    private QName readToStartFragment() throws XMLStreamException {
        XMLEvent nextEvent;
        do {
            nextEvent = this.eventReader.nextEvent();
        } while(!nextEvent.isStartElement() || !this.isFragmentRootElementName(((StartElement)nextEvent).getName()));

        return ((StartElement)nextEvent).getName();
    }

    private void readToEndFragment(QName fragmentRootElementName) throws XMLStreamException {
        XMLEvent nextEvent;
        do {
            nextEvent = this.eventReader.nextEvent();
        } while(!nextEvent.isEndElement() || !fragmentRootElementName.equals(((EndElement)nextEvent).getName()));

    }

    private boolean isFragmentRootElementName(QName name) {
        Iterator i$ = this.fragmentRootElementNames.iterator();

        QName fragmentRootElementName;
        do {
            do {
                if(!i$.hasNext()) {
                    return false;
                }

                fragmentRootElementName = (QName)i$.next();
            } while(!fragmentRootElementName.getLocalPart().equals(name.getLocalPart()));
        } while(StringUtils.hasText(fragmentRootElementName.getNamespaceURI()) && !fragmentRootElementName.getNamespaceURI().equals(name.getNamespaceURI()));

        return true;
    }

    private QName parseFragmentRootElementName(String fragmentRootElementName) {
        String name = fragmentRootElementName;
        String nameSpace = null;
        if(fragmentRootElementName.contains("{")) {
            nameSpace = fragmentRootElementName.replaceAll("\\{(.*)\\}.*", "$1");
            name = fragmentRootElementName.replaceAll("\\{.*\\}(.*)", "$1");
        }

        return new QName(nameSpace, name, "");
    }

}
