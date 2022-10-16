import { createWebHistory, createRouter } from "vue-router"
import PageNotFound from "@components/PageNotFound.vue"

// Instructor Pages
import InstructorLogin from "@components/Instructor/Login.vue"
import InstructorView from "@components/Instructor/Main.vue"
import InstructorTraineeList from "@components/Instructor/TraineeList.vue"
import InstructorCalendar from "@components/Instructor/Calendar.vue"
import InstructorSurveyList from "@components/Instructor/SurveyList.vue"
import InstructorSurveyCreate from "@components/Instructor/SurveyCreate.vue"
import InstructorList from "@components/Instructor/InstructorList.vue"

const router = createRouter({
    history : createWebHistory(),
    routes : [
        {path: "/", redirect: "/inslogin"},

        {path: "/inslogin", component: InstructorLogin},
        {path: "/instructor", component: InstructorView, redirect: "/instructor/trainee", children: [
            {path: "admin", component: InstructorList},
            {path: "trainee", component: InstructorTraineeList},
            {path: "calendar", component: InstructorCalendar},
            {path: "survey", component: InstructorSurveyList},
            {path: "survey/create", component: InstructorSurveyCreate},
        ]},


        {
            path : "/:pathMatch(.*)",
            name : "not-found",
            component : PageNotFound
        },
    ]
});

export default router