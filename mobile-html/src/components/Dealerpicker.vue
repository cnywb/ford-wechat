<template>
  <div :class="wrapper">
    <template v-if="type != 'mobile'">
      <select @change="getCities" v-model="currentProvince">
        <option :value="placeholders.province">{{ placeholders.province }}</option>
        <option v-for="(item, index) in provinces" :value="item">{{ item }}</option>
      </select>
      <template v-if="!onlyProvince">
        <select @change="getDealers" v-model="currentCity">
          <option :value="placeholders.city">{{ placeholders.city }}</option>
          <option v-for="(item, index) in cities" :value="item">{{ item }}</option>
        </select>
        <select v-if="!hideDealer" v-model="currentDealer">
          <option :value="placeholders.dealer">{{ placeholders.dealer }}</option>
          <option v-for="(item, index) in dealers " :value="item">{{ item }}</option>
        </select>
      </template>
    </template>
    <template v-else>
      <div :class="dealerHeader">
        <ul>
          <li :class="{'active': tab == 1}" @click="resetProvince">{{ currentProvince && !staticPlaceholder ? currentProvince : placeholders.province }}</li>
          <template v-if="!onlyProvince">
            <li v-if="showCityTab" :class="{'active': tab == 2}" @click="resetCity">{{ currentCity && !staticPlaceholder ? currentCity : placeholders.city }}</li>
            <li v-if="showDealerTab && !hideDealer" :class="{'active': tab == 3}">{{ currentDealer && !staticPlaceholder ? currentDealer : placeholders.dealer }}</li>
          </template>
        </ul>
      </div>
      <div :class="dealerContainer">
        <ul v-if="tab == 1">
          <li v-for="(item, index) in provinces" :class="{'active': item == currentProvince}" @click="chooseProvince(item)">{{ item }}</li>
        </ul>
        <template v-if="!onlyProvince">
          <ul v-if="tab == 2">
            <li v-for="(item, index) in cities" :class="{'active': item == currentCity}" @click="chooseCity(item)">{{ item }}</li>
          </ul>
          <ul v-if="tab == 3 && !hideDealer">
            <li v-for="(item, index) in dealers" :class="{'active': item == currentDealer}" @click="chooseDealer(item)">{{ item }}</li>
          </ul>
        </template>
      </div>
    </template>
  </div>
</template>

<script>
  import DISTRICTS from './dealers';

  const DEFAULT_CODE = "省份"

  export default {
    name: 'v-dealerpicker',
    props: {
      province: {type: [String, String], default: ''},
      city: {type: [String, String], default: ''},
      dealer: {type: [String, String], default: ''},
      type: {type: String, default: ''},
      hideDealer: {type: Boolean, default: false},
      onlyProvince: {type: Boolean, default: false},
      staticPlaceholder: {type: Boolean, default: false},
      placeholders: {
        type: Object,
        default() {
          return {
            province: '省份',
            city: '市区',
            dealer: '经销商',
          }
        }
      },
      wrapper: {type: String, default: 'dealer'},
      dealerHeader: {type: String, default: 'dealer-header'},
      dealerContainer: {type: String, default: 'dealer-container'},
    },
    data() {
      return {
        tab: 1,
        showCityTab: false,
        showDealerTab: false,
        provinces: [],
        cities: [],
        dealers: [],
        currentProvince: this.determineType(this.province) || this.placeholders.province,
        currentCity: this.determineType(this.city) || this.placeholders.city,
        currentDealer: this.determineType(this.dealer) || this.placeholders.dealer,
      }
    },
    created() {
      if (this.type != 'mobile') {
        this.provinces = this.getDistricts()
        this.cities = this.province ? this.getDistricts(this.getDealerCode(this.determineType(this.province))) : []
        this.dealers = this.city ? this.getDistricts(this.getDealerCode(this.determineType(this.city))) : []
      } else {
        if (this.dealer && !this.hideDealer && !this.onlyProvince) {
          this.tab = 3
          this.showCityTab = true
          this.showDealerTab = true
          this.dealers = this.getDistricts(this.getDealerCode(this.determineType(this.city)))
        } else if (this.city && this.hideDealer && !this.onlyProvince) {
          this.tab = 2
          this.showCityTab = true
          this.cities = this.getDistricts(this.getDealerCode(this.determineType(this.province)))
        } else {
          this.provinces = this.getDistricts()
        }
      }
    },
    watch: {
      currentProvince(vaule) {
        this.$emit('province', {
          code: this.getDealerCode(this.currentProvince),
          value: this.currentProvince,
        })
        if (this.onlyProvince) this.emit('selected')
      },
      currentCity(value) {
        this.$emit('city', {
          code: this.getDealerCode(value),
          value: value,
        })
        if (value != this.placeholders.city && this.hideDealer) this.emit('selected')
      },
      currentDealer(value) {
        this.$emit('dealer', {
          code: this.getDealerCode(value),
          value: value,
        })
        if (value != this.placeholders.dealer) this.emit('selected')
      },
      province(value) {
        this.currentProvince = this.province || this.placeholders.province
        this.cities = this.determineValue(this.currentProvince, this.placeholders.province)
      },
      city(value) {
        this.currentCity = this.city || this.placeholders.city
        this.dealers = this.determineValue(this.currentCity, this.placeholders.city)
      },
      dealer(value) {
        this.currentDealer = this.dealer || this.placeholders.dealer
      },
    },
    methods: {
      emit(name) {
        let data = {
          province: {
            code: this.getDealerCode(this.currentProvince),
            value: this.currentProvince,
          }
        }

        if (!this.onlyProvince) {
          this.$set(data, 'city', {
            code: this.getDealerCode(this.currentCity),
            value: this.currentCity,
          })
        }

        if (!this.onlyProvince || this.hideDealer) {
          this.$set(data, 'dealer', {
            code: this.getDealerCode(this.currentDealer),
            value: this.currentDealer,
          })
        }

        this.$emit(name, data)
      },
      getCities() {
        this.currentCity = this.placeholders.city
        this.currentDealer = this.placeholders.dealer
        this.cities = this.determineValue(this.currentProvince, this.placeholders.province)
        this.cleanList('dealers')
        if (this.cities == null) {
          this.emit('selected')
          this.tab = 1
          this.showCityTab = false
        }
      },
      getDealers() {
        this.currentDealer = this.placeholders.dealer
        this.dealers = this.determineValue(this.currentCity, this.placeholders.city)
        if (this.dealers == null) {
          this.emit('selected')
          this.tab = 2
          this.showDealerTab = false
        }
      },
      resetProvince() {
        this.tab = 1
        this.provinces = this.getDistricts()
        this.showCityTab = false
        this.showDealerTab = false
      },
      resetCity() {
        this.tab = 2
        this.showCityTab = true
        this.showDealerTab = false
        this.getCities()
      },
      chooseProvince(name) {
        this.currentProvince = name
        if (this.onlyProvince) return
        this.tab = 2
        this.showCityTab = true
        this.showDealerTab = false
        this.getCities()
      },
      chooseCity(name) {
        this.currentCity = name
        if (this.hideDealer) return
        this.tab = 3
        this.showCityTab = true
        this.showDealerTab = true
        this.getDealers()
      },
      chooseDealer(name) {
        this.currentDealer = name
      },
      getDealerCode(name) {
        for (var x in DISTRICTS) {
          for (var y in DISTRICTS[x]) {
            if (name == DISTRICTS[x][y]) {
              return y
            }
          }
        }
      },
      getCodeValue(code) {
        for (var x in DISTRICTS) {
          for (var y in DISTRICTS[x]) {
            if (code == y) {
              return DISTRICTS[x][y]
            }
          }
        }
      },
      getDistricts(code = DEFAULT_CODE) {
        return DISTRICTS[code] || null
      },
      determineValue(currentValue, placeholderValue) {
        if (currentValue == placeholderValue) {
          return []
        } else {
          return this.getDistricts(this.getDealerCode(currentValue))
        }
      },
      determineType(value) {
        let codeValue = this.getCodeValue(value);
        if (codeValue) {
          return codeValue;
        }
        return value
      },
      cleanList(name) {
        this[name] = []
      },
    }
  }
</script>

<style lang="scss" scoped>
  .dealer {
    color: #9caebf;
  }

  select {
    padding: .5rem .75rem;
    height: 40px;
    font-size: 1rem;
    line-height: 1.25;
    color: #464a4c;
    background-color: #fff;
    background-image: none;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    border: 1px solid rgba(0, 0, 0, .15);
    border-radius: .25rem;
    -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;

    option {
      font-weight: normal;
      display: block;
      white-space: pre;
      min-height: 1.2em;
      padding: 0px 2px 1px;
    }
  }

  ul {
    margin: 0;
    padding: 0;

    li {
      list-style: none;
    }
  }

  .dealer-header {
    background-color: #fff;

    ul {
      display: flex;
      justify-content: space-around;
      align-items: stretch;

      li {
        display: inline-block;
        padding: 10px 0px;

        &.active {
          border-bottom: #52697f solid 3px;
          color: #52697f;
        }
      }
    }
  }

  .dealer-container {
    background-color: #fff;

    ul {
      height: 10rem;
      overflow: scroll;

      li {
        text-align: center;
        padding: 8px 10px;
        border-top: 1px solid #f6f6f6;

        &.active {
          color: #52697f;
        }
      }
    }
  }
</style>
