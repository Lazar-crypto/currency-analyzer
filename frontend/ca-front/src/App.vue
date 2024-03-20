<template>
  <div class="container">
    <Header @analyze="fetchCandles" title="Currency analyzer" />
    <CandleChart ref="candleChart" />
    <Reports @delete-report="deleteReport" :reports="reports" />
  </div>
</template>

<script>
import axios from 'axios';
import Header from './components/Header.vue';
import CandleChart from './components/CandleChart.vue';
import Reports from './components/Reports.vue';

export default {
  name: 'App',
  components: {
    Header,
    CandleChart,
    Reports,
  },
  data() {
    return {
      pageNum: 0,
      reports: [],
    };
  },
  created() {
    this.fetchReports();
  },
  methods: {
    fetchCandles() {
      this.$refs.candleChart.fetchCandles();
    },
    deleteReport(id) {
      if (confirm('Are you sure?')) {
        axios
          .delete(`${process.env.API_URL}/report/${id}`)
          .then(() => {
            this.fetchReports();
          })
          .catch((error) => {
            console.error('Error while deleting report!', error);
          });
      }
    },
    async fetchReports() {
      const apiUrl = `${process.env.API_URL}/reports?pageNum=${this.pageNum}`;
      axios
        .get(apiUrl)
        .then((response) => {
          this.reports = response.data;
          console.log(this.reports);
        })
        .catch((error) => {
          console.error('Err while fetching reports', error);
        });
    },
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap');

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Poppins', sans-serif;
}

.container {
  max-width: 500px;
  margin: 30px auto;
  overflow: auto;
  min-height: 300px;
  border: 1px solid steelblue;
  padding: 30px;
  border-radius: 5px;
}

.btn {
  display: inline-block;
  background: #000;
  color: #fff;
  border: none;
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  font-size: 15px;
  font-family: inherit;
}

.btn:focus {
  outline: none;
}

.btn:active {
  transform: scale(0.98);
}

.btn-block {
  display: block;
  width: 100%;
}

select {
  padding: 0.5em;
  border-radius: 0.25em;
  border: 1px solid #ccc;
  background-color: white;
  margin-top: 0.5em;
  margin-bottom: 1em;
}
</style>
