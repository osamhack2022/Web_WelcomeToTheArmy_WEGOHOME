import { createWebHistory, createRouter } from "vue-router";
import PageNotFound from "@components/PageNotFound.vue"
import Login from "@components/Login.vue"

const router = createRouter({
    history : createWebHistory(),
    routes : [
        {path : "/", redirect: "/login"},
        {path : "/login", name : "Login", component : Login },
        
        {
            path : "/:pathMatch(.*)",
            name : "not-found",
            component : PageNotFound
        },
    ]
});

export default router;