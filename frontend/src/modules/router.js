import { createWebHistory, createRouter } from "vue-router"
import PageNotFound from "@components/PageNotFound.vue"

// Instructor Pages
import InstructorLogin from "@components/Instructor/Login.vue"
import InstructorView from "@components/Instructor/View.vue"
import InstructorTraineeList from "@components/Instructor/TraineeList.vue"
import InstructorCalendar from "@components/Instructor/Calendar.vue"
import InstructorSurveyList from "@components/Instructor/SurveyList.vue"
import InstructorSurveyCreate from "@components/Instructor/SurveyCreate.vue"
import InstructorList from "@components/Instructor/InstructorList.vue"

// Trainee Pages
import TraineeLogin from "@components/Trainee/Login.vue"
import TraineeView from "@components/Trainee/View.vue"
import TraineeMain from "@components/Trainee/Main.vue"
import TraineeCalendar from "@components/Trainee/Calendar.vue"
import TraineeCounsel from "@components/Trainee/Counsel.vue"
import TraineeGallery from "@components/Trainee/Gallery.vue"
import TraineeSurvey from "@components/Trainee/Survey.vue" 

const router = createRouter({
    history : createWebHistory(),
    routes : [
        {path: "/", redirect: "/trlogin"},

        {path: "/inslogin", component: InstructorLogin},
        {path: "/instructor", component: InstructorView, redirect: "/instructor/trainee", children: [
            {path: "admin", component: InstructorList},
            {path: "trainee", component: InstructorTraineeList},
            {path: "calendar", component: InstructorCalendar},
            {path: "survey", component: InstructorSurveyList},
            {path: "survey/create", component: InstructorSurveyCreate},
        ]},
        {path: "/trlogin", component: TraineeLogin},
        {path: "/trainee", component: TraineeView, redirect: "/trainee/main", children: [
            {path: "main", component: TraineeMain},
            {path: "calendar", component: TraineeCalendar},
            {path: "counsel", component: TraineeCounsel},
            {path: "gallery", component: TraineeGallery},
            {path: "survey/:id", component: TraineeSurvey},
        ]},


        {
            path : "/:pathMatch(.*)",
            name : "not-found",
            component : PageNotFound
        },
    ]
});

export default router