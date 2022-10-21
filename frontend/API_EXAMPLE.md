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