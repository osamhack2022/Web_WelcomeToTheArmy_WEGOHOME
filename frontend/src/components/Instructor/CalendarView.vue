<template>
    <v-calendar
        class="custom-calendar max-w-full"
        :masks="masks"
        :attributes="attributes"
        disable-page-swipe
        is-expanded
    >
        <template v-slot:day-content="{ day, attributes }">
        <div class="flex flex-col h-full z-10 overflow-hidden">
            <span class="day-label text-sm text-gray-900">{{ day.day }}</span>
            <div class="flex-grow overflow-y-auto overflow-x-auto">
            <p
                v-for="attr in attributes"
                :key="attr.key"
                class="leading-tight rounded-sm p-1 mt-0 mb-1 schedule-box"
                :class="attr.customData.color">
                {{ attr.customData.title }}
            </p>
            </div>
        </div>
        </template>
    </v-calendar>
</template>

<script>
import useAxios from "@app_modules/axios.js"

const { axiosGet } = useAxios()

export default {
    data() {
        return {
          masks: {
            weekdays: 'WWW',
          },
          attributes: [
            {
              key: "today",
              highlight: true,
              dates: new Date().toDateString(),
              customData: {color: "bg-today"}
            }
          ],
        };
    },
    methods: {
      createCalendarAttr(datas) {
        var attrs = []
        var i = 1
        for (var data of datas) {
          var attr = {}
          attr.key = i++
          attr.cutstomData = {
            title: data.name,
            color: "bg-blue",
          }
          attr.dates = {
            start: data.startDate,
            end: data.endDate,
          }
          attrs.push(attr)
        }
        return attrs
      }
    },
    created() {
      const onSuccess = (data) => {
        this.attributes = this.createCalendarAttr(data.data)
      }
      const onFailed = (data) => {
          console.error("일정을 불러오는 과정에서 오류가 발생했습니다..\n"+data.response.data.message)
      }
      axiosGet("schedule", onSuccess, onFailed)
    },
};
</script>

<style>
.schedule-box {
  font-size: 1vw !important;
  color: white;
}
.bg-blue{background-color: blue;}


.flex-col {
  flex-direction: column;
}

.flex {
  display: flex;
}
.flex-grow {
  flex-grow: 1;
}
.h-full {
  height: 100%;
}
.bg-red-600 {
  background-color: #e53e3e;
}
.today {
  background-color: #d2e1ec;
}
.bg-teal-500 {
  background-color: #38b2ac;
}
.bg-pink-500 {
  background-color: #ed64a6;
}
.bg-indigo-500 {
  background-color: #667eea;
}
.overflow-y-scroll {
  overflow-y: scroll;
}
.overflow-x-auto {
  overflow-x: auto;
}
.z-10 {
  z-index: 10;
}
.overflow-hidden {
  overflow: hidden;
}
.text-gray-900 {
  color: #1a202c;
}
.vc-h-full {
  height: 100%;
}
.custom-calendar.vc-container {
  --day-border: 1px solid #b8c2cc;
  --day-border-highlight: 1px solid #b8c2cc;
  --day-width: 35px;
  --day-height: 90px;
  --weekday-bg: #f8fafc;
  --weekday-border: 1px solid #eaeaea;
  border-radius: 0;
  width: auto;
}
.custom-calendar.vc-container .vc-weeks {
  padding: 0;
  height: 80vh;
  grid-template-rows: 33px repeat(6, 1fr);
}
.custom-calendar.vc-container .vc-header {
  background-color: #f1f5f8;
  padding: 10px 0;
}
.custom-calendar.vc-container .vc-weekday {
  background-color: var(--weekday-bg);
  border-bottom: var(--weekday-border);
  border-top: var(--weekday-border);
  padding: 5px 0;
}
.vc-border {
  border-width: 1px;
}
.custom-calendar.vc-container .vc-day:not(.on-right) {
  border-right: var(--day-border);
}
.custom-calendar.vc-container .vc-day:not(.on-bottom) {
  border-bottom: var(--day-border);
}
.custom-calendar.vc-container .vc-day.weekday-1,
.custom-calendar.vc-container .vc-day.weekday-7 {
  background-color: #eff8ff;
}

.custom-calendar.vc-container .vc-day {
  padding: 0 5px 3px;
  text-align: left;
  /*min-height: var(--day-height);*/
  min-width: var(--day-width);
  background-color: #fff;
}
.vc-day {
  position: relative;
  min-height: var(--day-min-height);
  width: 100%;
  height: 100%;
  z-index: 1;
}
.text-center {
  text-align: center;
}
.section {
}
.max-w-full {
  max-width: 100%;
}
.bg-blue-500 {
  background-color: #4299e1;
}

.text-xs {
  font-size: 0.75rem;
}
.text-white {
  color: #fff;
}
.p-1 {
  padding: 0.25rem;
}
.mb-1 {
  margin-bottom: 0.25rem;
}
.mt-0 {
  margin-top: 0;
}
.leading-tight {
  line-height: 1.25;
}
.rounded-sm {
  border-radius: 0.125rem;
}
::-webkit-scrollbar {
  width: 0px;
}
::-webkit-scrollbar-track {
  display: none;
}
.vc-highlight { display: none; }
.vc-highlights { background-color: pink; }
</style>
