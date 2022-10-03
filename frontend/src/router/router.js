import { createWebHistory, createRouter } from "vue-router";
import PageNotFound from "@components/PageNotFound.vue";
import InstructorLogin from "@components/InstructorLogin.vue";
import InstructorView from "@components/InstructorView.vue";

const router = createRouter({
    history : createWebHistory(),
    routes : [
        {path: "/", redirect: "/inslogin"},
        {path: "/inslogin", component: InstructorLogin},
        {path: "/insmain", component: InstructorView},

        {
            path : "/:pathMatch(.*)",
            name : "not-found",
            component : PageNotFound
        },
    ]
});

export default router;