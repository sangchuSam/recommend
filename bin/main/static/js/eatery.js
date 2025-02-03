/**
 * 
 */

function fetchGuestId() {
    fetch("/guest/id")
        .then(response => response.text())
        .then(data => {
            document.getElementById("guestId").innerText = data;
        })
        .catch(error => console.error("Error fetching Guest ID:", error));
}
window.onload = fetchGuestId;


// Guest 정보 저장 API 호출
function saveGuest() {
    console.log("🔹 saveGuest() 실행됨!");  // ✅ 함수 실행 확인

    let gender = document.getElementById("gender").value;
    let ageGroup = document.getElementById("ageGroup").value;
    let preferences = document.getElementById("preferences").value;

    console.log("🎯 선택된 값:", gender, ageGroup, preferences);  // ✅ 선택된 값 확인

    if (!gender || !ageGroup || !preferences) {
        alert("모든 항목을 선택해주세요!");
        return;
    }

    let guestData = {
        gender: gender,
        ageGroup: ageGroup,
        preferences: preferences
    };

    console.log("📡 서버로 전송할 데이터:", guestData);  // ✅ 서버로 보낼 데이터 확인

    fetch("/guest/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(guestData)
    })
    .then(response => response.json())
    .then(data => {
        alert("저장 완료!");
        console.log("Saved:", data);
    })
    .catch(error => console.error("저장 오류:", error));
}


window.onload = fetchGuestId; // 페이지 로드 시 Guest ID 가져오기

function fetchRecommendations() {
    let guestId = document.getElementById("guestId").innerText.trim();
    let preferences = document.getElementById("preferences").value;

    if (!guestId || !preferences) {
        alert("사용자 정보를 먼저 저장하세요!");
        return;
    }

    fetch(`/guest/recommend?preferences=${encodeURIComponent(preferences)}`)
        .then(response => response.json())
        .then(data => {
            console.log("✅ Flask에서 받은 데이터:", data);

            let recommendationsList = document.getElementById("recommendations");
            let noRecommendations = document.getElementById("noRecommendations");

            if (!recommendationsList) {
                console.error("❌ 오류: `#recommendations` 요소를 찾을 수 없습니다.");
                return;
            }

            recommendationsList.innerHTML = ""; // 기존 목록 삭제

            if (!data || !data.recommendations || data.recommendations.length === 0) {
                noRecommendations.style.display = "block";  // ❌ "추천된 음식점 없음" 표시
                recommendationsList.style.display = "none"; // 🔹 목록 숨기기
                return;
            }

            noRecommendations.style.display = "none"; // ❌ 메시지 숨기기
            recommendationsList.style.display = "block"; // 🔹 목록 표시

            data.recommendations.forEach(item => {
                let li = document.createElement("li");
                li.innerText = `${item.restaurantId} - ${item.name} - ${item.category} - ${item.priceLevel}`;
                recommendationsList.appendChild(li);
            });
        })
        .catch(error => console.error("❌ 추천 API 호출 오류:", error));
}
