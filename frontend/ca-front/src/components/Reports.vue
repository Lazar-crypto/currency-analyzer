<template>
  <div :key="report.reportname" v-for="report in reports">
    <Report
      @delete-report="$emit('delete-report', report.id)"
      :report="report"
    />
  </div>
</template>

<script>
import Report from './Report.vue';

export default {
  name: 'Reports',
  props: {
    reports: Array,
  },
  components: {
    Report,
  },
  emits: ['delete-report'],
};
</script>
