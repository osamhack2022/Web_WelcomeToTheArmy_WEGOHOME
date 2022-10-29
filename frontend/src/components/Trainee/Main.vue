<template>
    <DayLeftWidget class="widget-comp"/>
    <router-link to="/trainee/calendar">
        <TomorrowScheduleWidget class="widget-comp"/>
    </router-link>
    <SurveyWidget class="widget-comp"/>
    <router-link to="/trainee/counsel">
        <div class="widget-comp widget">
            <p class="help">당신은 도움받을 권리가 있습니다.</p>
            <p class="help-big">소대장 / 상담장교와의 대화 바로가기</p>
        </div>
    </router-link>
    <div class="widget-comp">
        <router-link to="/trainee/gallery">
            <div class="widget-sm widget-left">
                <img class="widget-img" src="@/assets/images/training_photo.png" alt="training gallery" />
                <h3 class="widget-title">훈련 사진</h3> 
            </div>
        </router-link>
        <a href="https://www.youtube.com/watch?v=Zy3eX8GZ1s0&list=PLtFaCRM4Ud77rQqq3PUhJwdadnSc4riqr" target="_blank">
            <div class="widget-sm widget-right">
                <img class="widget-img" src="@/assets/images/HighestPower.jpg" alt="training gallery" />
                <h3 class="widget-title">정신전력교육</h3>
            </div>
        </a>
    </div>
</template>

<script>
import DayLeftWidget from "./WidgetDayLeft.vue"
import SurveyWidget from "./WidgetSurvey.vue"
import TomorrowScheduleWidget from "./WidgetTomorrowSchedules.vue"

export default {
    components: {
        DayLeftWidget,
        SurveyWidget,
        TomorrowScheduleWidget,
    }
}
</script>

<style scoped>
* {
    margin: 0; padding: 0;
}
a {
    color: black;
    text-decoration: none;
}
.widget-comp {
    margin: 12px 0px;
    width: 100%;
}
.widget {
    width: 100%; padding: 10px 5px;
    border: solid 1px; border-radius: 8px;
}
.help {
    font-size: 12px;
}
.help-big {
    font-size: 16px; font-weight: 500;
}
.widget-sm {
    width: 49%; aspect-ratio: 2/1.5 ;
    border: solid 1px; border-radius: 8px;
    text-align: center; position: relative;
    color: black;
    overflow: hidden;
}
.widget-left { float: left; }
.widget-right { float:right; }
.widget-img {
    width: 120%; height: 120%;
    margin-left: -10%; margin-top: -10%;
    object-fit: cover;
    -webkit-filter: blur(3px); 
    -moz-filter: blur(3px); 
    -o-filter: blur(3px); 
    -ms-filter: blur(3px); 
    filter: blur(3px);
}
.widget-title {
    color: white !important;
    font-size: 32px; font-weight: bold;
    position: absolute;
    left: 50%; top: 50%; transform: translate(-50%, -50%);
    width: 100%;
    text-shadow: 2px 2px 1px black;
}
</style>