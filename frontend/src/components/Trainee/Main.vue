<template>
    <DayLeftWidget class="widget-comp"/>
    <TomorrowScheduleWidget class="widget-comp"/>
    <SurveyWidget class="widget-comp"/>
    <div class="widget-comp widget">
        <p class="help">당신은 도움받을 권리가 있습니다.</p>
        <p class="help-big">소대장 / 상담장교와의 대화 바로가기</p>
    </div>
    <div class="widget-comp">
        <div class="widget-sm">
asdf
        </div>
        <div class="widget-sm">
asdf
        </div>
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
.widget-comp {
    margin: 12px 0px;
    width: 100%;
}
.widget {
    width: 100%; padding: 10px 5px;
    border: solid 1px; border-radius: 8px;
}
.widget-sm {
    width: 30%;padding: 10px 5px;
    border: solid 1px; border-radius: 8px;
}
.help {
    font-size: 12px; font-weight: bold;
}
.help-big {
    font-size: 16px; font-weight: bold;
}
</style>