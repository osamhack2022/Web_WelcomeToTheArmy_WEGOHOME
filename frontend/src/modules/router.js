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
import InstructorSurveyResult from "@components/Instructor/SurveyResult.vue"
import InstructorCounsel from "@components/Instructor/Counsel.vue"
import InstructorGallery from "@components/Instructor/Gallery.vue"

// Trainee Pages
import TraineeLogin from "@components/Trainee/Login.vue"
import TraineeView from "@components/Trainee/View.vue"
import TraineeMain from "@components/Trainee/Main.vue"
import TraineeCalendar from "@components/Trainee/Calendar.vue"
import TraineeGallery from "@components/Trainee/Gallery.vue"
import TraineeSurvey from "@components/Trainee/Survey.vue" 
import TraineeCounsel from "@components/Trainee/Counsel.vue"
import TraineeCounselCreate from "@components/Trainee/CounselCreate.vue"
import TraineeCounselSubmit from "@components/Trainee/CounselSubmit.vue"

// Index Page
import Index from "@components/Index.vue"

const router = createRouter({
    history : createWebHistory(),
    routes : [
        {path: "/", component: Index},

        {path: "/inslogin", component: InstructorLogin},
        {path: "/instructor", component: InstructorView, redirect: "/instructor/trainee", meta: {insAuthRequired: true}, children: [
            {path: "admin", component: InstructorList},
            {path: "trainee", component: InstructorTraineeList},
            {path: "calendar", component: InstructorCalendar},
            {path: "survey", component: InstructorSurveyList},
            {path: "survey/create", component: InstructorSurveyCreate},
            {path: "survey/:id", component: InstructorSurveyResult},
            {path: "counsel", component: InstructorCounsel}, 
            {path: "gallery", component: InstructorGallery},
        ]},
        {path: "/trlogin", component: TraineeLogin},
        {path: "/trainee", component: TraineeView, redirect: "/trainee/main", meta: {trAuthRequired: true}, children: [
            {path: "main", component: TraineeMain},
            {path: "calendar", component: TraineeCalendar},
            {path: "counsel", component: TraineeCounsel },
            {path: "createCounsel", component: TraineeCounselCreate},
            {path: "counselSubmit", component: TraineeCounselSubmit},
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

router.beforeEach(function (to, from, next) {
    if (to.matched.some(function(routeInfo) {
        return routeInfo.meta.insAuthRequired;
    })) {
        if (localStorage.getItem("traineeLoginToken")){
            alert("이 페이지는 관리자 전용 페이지 입니다.\n로그아웃합니다.")
            localStorage.removeItem("traineeLoginToken")
        }
        if (localStorage.getItem("instructorLoginToken")){
            next()
            return
        }
        router.push("/inslogin")
    } else if (to.matched.some(function(routeInfo) {
        return routeInfo.meta.trAuthRequired;
    })) {
        if (localStorage.getItem("instructorLoginToken")){
            alert("이 페이지는 훈련병 전용 페이지 입니다.\n로그아웃합니다.")
            localStorage.removeItem("instructorLoginToken")
        }
        if (localStorage.getItem("traineeLoginToken")){
            next()
            return
        }
        router.push("/trlogin")
    } else {
        next()
    }
})
export default router