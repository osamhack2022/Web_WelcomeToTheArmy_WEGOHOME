# API로 전달되는 데이터 예시
## /survey/create
```
{
  "title": "테스트 조사전달",
  "questions": [
    {
      "type": "주관식",
      "title": "이름이 뭐에용",
      "description": "주관식 질문 예시",
      "options": [
        "",
        ""
      ]
    },
    {
      "type": "객관식",
      "title": "좋아하는 베라 맛은?",
      "description": "객관식 질문 예시",
      "options": [
        "민트초코",
        "초코나무숲",
        "슈팅스타"
      ]
    }
  ]
}
```

## /schedule POST
```
{
  "name": "수리남 여행",
  "range": "기본군사훈련단",
  "start_date": "2022-10-28",
  "end_date": "2022-10-31"
}
```

## /schedule GET
```
[
  {
    key: 1,
    customData: {
      title: "수리남 여행",
      class: 'bg-blue-500 text-white',
    },
    dates: { start: "2022-10-28", end: "2022-10-31" }
  },
  {
    key: 2,
    customData: {
      title: "발닦고 자기", 
      class: 'bg-blue-500 text-white',
    },
    dates: { start: "2022-10-15", end: "2022-10-15" }
  }
]
```

## /schedules?date=2022-10-23 GET
```
[
  {
    title: "아침뜀걸음",
    start: "0700"
  },
  {
    title: "조식",
    start: "0730"
  },
  {
    title: "유격체조 실습 (제1전천후)",
    start: "0900"
  },
  {
    title: "중식",
    start: "1130"
  },
  {
    title: "유격훈련 실습 (유격훈련장)"m
    start: "1300"
  },
  {
    title: "부대 미화의 날",
    start: "1600"
  },
  {
    title: "석식",
    start: "1700"
  },
  {
    title: "개인정비",
    start: "1800"
  }
]
```

## /survey/answer
```
{
  "response_time": "2022-10-26T13:15:51.252Z",
  "answers": [
    {
      "id": 0,
      "answer": "0"
    },
    {
      "id": 1,
      "answer": "1"
    }
  ]
}
```