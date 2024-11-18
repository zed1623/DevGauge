import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Analyse from '../views/Analyse.vue';
import Personal from '../views/Personal.vue';
import Guide from '../views/Guide.vue';
import Overview from '../views/Overview.vue';
import Chat from '../views/Chat.vue';
import Login from '../views/Login.vue';
import Nav from '../views/Nav.vue';
import User from '../views/User.vue';
import UserRank from '../views/UserRank.vue';
import ProjectRank from '../views/ProjectRank.vue';

const routes = [
    {
        path: '/home',
        name: 'Home',
        component: Home,
        // meta: { requiresAuth: true },
    },
    {
        path: '/analyse',
        name: 'Analyse',
        component: Analyse,
        // meta: { requiresAuth: true },
    },
    {
        path: '/personal',
        name: 'Personal',
        component: Personal,
        // meta: { requiresAuth: true },
    },
    {
        path: '/',
        name: 'Guide',
        component: Guide,
        // meta: { requiresAuth: true },
    },
    {
        path: '/overview',
        name: 'Overview',
        component: Overview,
        // meta: { requiresAuth: true },
    },
    {
        path: '/chat',
        name: 'Chat',
        component: Chat,
        // meta: { requiresAuth: true },
    },
    {
        path: '/nav',
        name: 'Nav',
        component: Nav,
        // meta: { requiresAuth: true },
    },
    {
        path: '/user',
        name: 'User',
        component: User,
        // meta: { requiresAuth: true },
    },
    {
        path: '/projectRank',
        name: 'ProjectRank',
        component: ProjectRank,
        // meta: { requiresAuth: true },
    },
    {
        path: '/userRank',
        name: 'UserRank',
        component: UserRank,
        // meta: { requiresAuth: true },
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// router.beforeEach((to, from, next) => {
//     const isAuthenticated = localStorage.getItem('isAuthenticated');
//     console.log("authen", isAuthenticated);

//     // 如果目标路由需要认证并且未登录，跳转到登录页
//     if (to.meta.requiresAuth && !isAuthenticated) {
//         next({ name: 'Login', query: { redirect: to.fullPath } }); // 保存当前路由路径，用于登录后跳转
//     } else {
//         next(); // 允许访问
//     }
// });

export default router;