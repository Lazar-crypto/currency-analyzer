<template>
  <div id="chart"></div>
  <form @submit.prevent="fetchCandles">
    <div id="form">
      <div class="form-control">
        <label for="symbol-select">Pick symbol:</label>
        <select id="symbol-select" v-model="symbol">
          <option value="BTCUSDT">Bitcoin - BTCUSDT</option>
          <option value="ETHUSDT">Ethereum - ETHUSDT</option>
          <option value="BNBUSDT">Binance Coin - BNBUSDT</option>
          <option value="ADAUSDT">Cardano - ADAUSDT</option>
          <option value="XRPUSDT">Ripple - XRPUSDT</option>
          <option value="SOLUSDT">Solana - SOLUSDT</option>
          <option value="DOTUSDT">Polkadot - DOTUSDT</option>
          <option value="DOGEUSDT">Dogecoin - DOGEUSDT</option>
          <option value="SHIBUSDT">Shiba Inu - SHIBUSDT</option>
          <option value="LTCUSDT">Litecoin - LTCUSDT</option>
        </select>
      </div>
      <div class="form-control">
        <label for="interval-select">Pick interval:</label>
        <select id="interval-select" v-model="interval">
          <option value="1m">1 minute</option>
          <option value="3m">3 minutes</option>
          <option value="5m">5 minutes</option>
          <option value="15m">15 minutes</option>
          <option value="30m">30 minutes</option>
          <option value="1h">1 hour</option>
          <option value="2h">2 hours</option>
          <option value="4h">4 hours</option>
          <option value="6h">6 hours</option>
          <option value="8h">8 hours</option>
          <option value="12h">12 hours</option>
          <option value="1d">1 day</option>
          <option value="3d">3 days</option>
          <option value="1w">1 week</option>
          <option value="1M">1 month</option>
        </select>
      </div>
      <div class="form-control">
        <label for="interval-select">Pick size:</label>
        <select id="interval-select" v-model="size">
          <option value="sm">Small</option>
          <option value="md">Medium</option>
          <option value="l">Large</option>
        </select>
      </div>
    </div>
  </form>
  <apexchart
    type="candlestick"
    height="350"
    :options="chartOptions"
    :series="series"
  ></apexchart>
</template>

<script>
import axios from 'axios';
import VueApexCharts from 'vue3-apexcharts';

export default {
  name: 'CandleChart',
  components: { apexchart: VueApexCharts },
  data() {
    return {
      series: [],
      chartOptions: {
        chart: {
          type: 'candlestick',
          height: 350,
        },
        title: {
          text: 'Price($)',
          align: 'left',
        },
        xaxis: {
          type: 'datetime',
        },
        yaxis: {
          tooltip: {
            enabled: true,
          },
        },
      },
      symbol: 'BTCUSDT',
      interval: '1d',
      size: 'sm',
    };
  },
  created() {
    this.fetchCandles();
  },
  methods: {
    async fetchCandles() {
      const apiUrl = `${process.env.API_URL}/candles?symbol=${this.symbol}&interval=${this.interval}&size=${this.size}`;
      axios
        .get(apiUrl)
        .then((response) => {
          console.log(response);
          const candleData = response.data.map((candle) => ({
            x: new Date(candle.openTime),
            y: [
              candle.openPrice,
              candle.highPrice,
              candle.lowPrice,
              candle.closePrice,
            ],
          }));
          this.series = [{ data: candleData }];
          this.chartOptions.title.text = `${this.symbol} Prices`;
        })
        .catch((error) => {
          console.error('Err while fetching candle data', error);
        });
    },
  },
};
</script>

<style scoped>
.add-form {
  margin-bottom: 40px;
}

.form-control {
  margin: 5px 0;
}

.form-control label {
  width: 100%;
  height: 40px;
  margin: 5px;
  padding: 3px 7px;
  font-size: 17px;
}

#form {
  margin-bottom: 15px;
}
</style>
