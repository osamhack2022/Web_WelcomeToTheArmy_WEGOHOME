# API로 전달되는 데이터 예시
## /survey/create
```
{
  "title": "테스트 조사전달",
  "question": [
    {
      "type": "주관식",
      "title": "주관식질문",
      "description": "이름이 뭐에용",
      "options": [
        "",
        ""
      ]
    },
    {
      "type": "객관식",
      "title": "객관식질문",
      "description": "좋아하는 베라 맛은?",
      "options": [
        "민트초코",
        "초코나무숲",
        "슈팅스타"
      ]
    }
  ]
}
```