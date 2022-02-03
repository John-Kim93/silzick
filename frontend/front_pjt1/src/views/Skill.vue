<template>
  <div class="d-flex" style="color:white">
    <img src="https://ifh.cc/g/1WPkxv.png" id="bg" alt="bgImg">
    <!-- v-if 사용해서 직업별 보이는 요소가 다르게 설정 -->
    <!-- 1.노트주인 -->
    <div v-if="노트주인" class="center_position">
      <h1>노트작성!</h1>
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning">단, 상대방의 직업을 정확하게 맞추지 못하면 본인이 사망하게 됩니다.</h4>
      <h5>
        <select class="select_list">
          <option selected disabled>참가자 목록</option>
          <option>참가자1</option>
          <option>참가자2</option>
          <option>참가자3</option>
          <option>참가자4</option>
          <option>참가자5</option>
          <option>참가자6</option>
          <!-- <option>참가자7</option>
          <option>참가자8</option>
          <option>참가자9</option> -->
        </select>
        은/는
        <select class="select_list">
          <option selected disabled>직업 목록</option>
          <!-- 노트주인은 경찰 측만 잡으면 되기 때문에 노트주인 측은 주석처리 -->
          <!-- <option>노트주인</option>
          <option>추종자</option> -->
          <option>경찰총장</option>
          <option>보디가드</option>
          <option>방송인</option>
          <option>경찰</option>
        </select>
        이다.
        <b-button variant="outline-light">이름쓰기</b-button>
      </h5>
      <div>
        <button class="skill_button_note">KILL!</button>
      </div>
    </div>

    <!-- 2.추종자 -->
    <div v-if="추종자" class="center_position">
      <h1>노트작성!</h1>
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning">단, 상대방의 직업을 정확하게 맞추지 못하면 본인이 사망하게 됩니다.</h4>
      <h5>
        <select class="select_list">
          <option selected disabled>참가자 목록</option>
          <option>참가자1</option>
          <option>참가자2</option>
          <option>참가자3</option>
          <option>참가자4</option>
          <option>참가자5</option>
          <option>참가자6</option>
          <!-- <option>참가자7</option>
          <option>참가자8</option>
          <option>참가자9</option> -->
        </select>
        은/는
        <select class="select_list">
          <option selected disabled>직업 목록</option>
          <!-- 추종자는 경찰 측만 잡으면 되기 때문에 노트주인 측은 주석처리 -->
          <!-- <option>노트주인</option>
          <option>추종자</option> -->
          <option>경찰총장</option>
          <option>보디가드</option>
          <option>방송인</option>
          <option>경찰</option>
        </select>
        이다.
      </h5>
        <!-- <button>이름쓰기</button> -->
      <div class="my-3">
        <button class="skill_button_note">KILL!</button>
      </div>
    </div>

    <!-- 3.경찰총장 -->
    <div v-if="경찰총장" class="center_position">
      <h1>탐지하기!</h1>
      <h5>명함 교환 시 거짓말 탐지가 가능합니다.</h5>
      <div class="my-3">
        <button class="skill_button_police">ACTIVATE!</button>
      </div>
    </div>

    <!-- 4.보디가드 -->
    <div v-if="보디가드" class="center_position">
      <h1>보호하기!</h1>
      <h5>
        <select class="select_list">
          <option selected disabled>참가자 목록</option>
          <option>참가자1</option>
          <option>참가자2</option>
          <option>참가자3</option>
          <option>참가자4</option>
          <option>참가자5</option>
          <option>참가자6</option>
          <!-- <option>참가자7</option>
          <option>참가자8</option>
          <option>참가자9</option> -->
        </select>
        을/를 보호합니다.
      </h5>
      <div>
        <button class="skill_button_police my-3">SAVE!</button>
      </div>
    </div>

    <!-- 5.방송인 -->
    <div v-if="방송인" class="center_position">
      <h1>방송하기!</h1>
      <h5>
        <input type="text" class="" style="background-color:transparent; width:500px" placeholder="방송할 멘트를 입력하세요">
      </h5>
      <div>
        <button class="skill_button_police my-3">BROADCAST!</button>
      </div>
    </div>

    <!-- 6.경찰 -->
    <div class="center_position">
      <h1>검거하기!</h1>
      <b-icon icon="exclamation-triangle" font-scale="4" variant="warning"></b-icon>
      <h4 class="link-warning">단, 상대방의 직업을 정확하게 맞추지 못하면 본인이 사망하게 됩니다.</h4>
      <h5>
        <select class="select_list">
          <option selected disabled>참가자 목록</option>
          <option>참가자1</option>
          <option>참가자2</option>
          <option>참가자3</option>
          <option>참가자4</option>
          <option>참가자5</option>
          <option>참가자6</option>
          <!-- <option>참가자7</option>
          <option>참가자8</option>
          <option>참가자9</option> -->
        </select>
        은/는
        <select class="select_list">
          <option selected disabled>직업 목록</option>
          <option>노트주인</option>
          <option>추종자</option>
          <!-- 경찰은 노트주인 측만 잡으면 되기 때문에 경찰측은 주석처리 -->
          <!-- <option>경찰총장</option>
          <option>보디가드</option>
          <option>방송인</option>
          <option>경찰</option> -->
        </select>
        이다.
      </h5>
      <div>
        <button class="skill_button_police my-3">ARREST!</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.center_position {
  position: fixed;
  display: block;
  width: 50%;
  height: 50%;
  top: 38%;
  left: 25%;
  justify-content: center;
  text-align: center;
  align-items: center;
}
.skill_button_police {
	background-color:#1186cf;
	border-radius:28px;
	border:1px solid #4e6096;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #283966;
}
.skill_button_police:hover {
  background-color:#476e9e;
}
.skill_button_police:active {
	position:relative;
	top:1px;
}
.skill_button_note {
	background-color:#fc5230;
	border-radius:28px;
	border:1px solid #d02718;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #810e05;
}
.skill_button_note:hover {
	background-color:#d65246;
}
.skill_button_note:active {
	position:relative;
	top:1px;
}
.select_list {
  background-color: transparent;
  background: black;
  color: white;
  border-radius: 8px;
  text-align: center;
}
input[type=text] {
  border: solid white;
  border-radius: 5px;
  color: transparent;
}
input[type=text]:focus {
  border: solid white;
  color: white
}
</style>