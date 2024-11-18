import { createApp } from 'vue';
import './style.css';
import { createPinia } from 'pinia';
import App from './App.vue';
// 引入 Element Plus 和样式
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
// 引入路由
import router from './router';

// 引入 Font Awesome
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

// 导入所需的图标
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';

import { faChartBar, faProjectDiagram, faUsers, faUserFriends, faChartPie, faChartLine, faCode, faQuestionCircle, faCodeBranch, faUserPlus } from '@fortawesome/free-solid-svg-icons';

library.add(faChartBar, faProjectDiagram, faUsers, faUserFriends, faChartPie, faChartLine, faCode, faQuestionCircle, faCodeBranch, faUserPlus);


// 将所有图标添加到库中


const app = createApp(App);
const pinia = createPinia();

// 注册 FontAwesomeIcon 组件
app.component('font-awesome-icon', FontAwesomeIcon);

// 注册 Element Plus 的图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

app.use(ElementPlus);
app.use(router);
app.use(pinia);
app.mount('#app');
