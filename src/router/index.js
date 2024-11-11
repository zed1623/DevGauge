import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Analyse from '../views/Analyse.vue';
import Personal from '../views/Personal.vue';
import Location from '../views/Location.vue';
import Overview from '../views/Overview.vue';
import Chat from '../views/Chat.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/analyse',
        name: 'Analyse',
        component: Analyse,
    },
    {
        path: '/personal',
        name: 'Personal',
        component: Personal,
    },
    {
        path: '/location',
        name: 'Location',
        component: Location,
    },
    {
        path: '/overview',
        name: 'Overview',
        component: Overview,
    },
    {
        path: '/chat',
        name: 'Chat',
        component: Chat,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;