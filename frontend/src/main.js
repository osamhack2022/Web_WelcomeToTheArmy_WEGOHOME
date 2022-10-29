import { createApp, h } from 'vue'
import App from './App.vue'
import './index.css'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.esm.min.js'

import router from "@app_modules/router"

import 'v-calendar/dist/style.css';
import VCalendar from 'v-calendar';

const app  = createApp({
    render: () => h(App)
});

app.use(router);
app.use(VCalendar, {})
app.mount('#app');
