import 'ant-design-vue/dist/reset.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { Button, Layout, Menu, Avatar } from 'ant-design-vue'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(Layout)
app.use(Menu)
app.use(Avatar)
app.use(Button)

app.mount('#app')
