<template>
  <div class="container" style="color:white">
    <img src="https://ifh.cc/g/1WPkxv.png" id="bg" alt="bgImg">
    <!--참가자 전체 CAM : total_cam-->
    <div class="d-flex justify-content-around total_cam">
      <!--참가자 1 CAM : private_cam-->
      <div class="private_cam_6">
        참가자1
      </div>
      <!--참가자 2 CAM : private_cam-->
      <div class="private_cam_6">
        참가자2
      </div>
      <!--참가자 3 CAM : private_cam-->
      <div class="private_cam_6">
        참가자3
      </div>
      <!--참가자 4 CAM : private_cam-->
      <div class="private_cam_6">
        참가자4
      </div>
      <!--참가자 5 CAM : private_cam-->
      <div class="private_cam_6">
        참가자5
      </div>
      <!--참가자 6 CAM : private_cam-->
      <div class="private_cam_6">
        참가자6
      </div>
    </div>

    <!--다음 명함 교환 까지(타이머) : timer-->
    <div class="timer">
      <p class="d-flex align-items-center">
      다음 명함 교환까지
      <br>
      XX초 남았습니다.</p>
    </div>

    <!--직업/직업설명/능력/승리조건 : explain-->
    <div class="explain">
      <!--직업-->
      <div class="explain_job dotted_line">
        <strong>직업</strong>
      </div>
      <!--직업설명-->
      <div class="explain_job_description dotted_line">
        직업설명
      </div>
      <!--능력-->
      <div class="explain_skill dotted_line">
        능력
      </div>
      <!--승리조건-->
      <div class="explain_win">
        승리조건
      </div>
    </div>

    <!--채팅 전체 : chat-->
    <div class="d-flex chat">
      <!--채팅 내역 : chat_list-->
      <div class="chat_list">
        채팅
      </div>
      <!--채팅 입력 : chat_input-->
      <input class="chat_input" type="text" placeholder="메세지를 입력하세요">
    </div>

    <!--의심 직업 : doubt-->
    <div class="doubt">
      <p>추측 현황</p>
      <div class="d-flex justify-content-around align-items-center">
        <div class="d-inline">
          <p class="d-inline">참가자 1 : </p>
          <select class="doubt_select">
            <option selected disabled>------------</option>
            <option>노트주인</option>
            <option>추종자</option>
            <option>경찰총장</option>
            <option>보디가드</option>
            <option>방송인</option>
            <option>경찰</option>
          </select>
        </div>
        <div class="d-inline">
          <p class="d-inline">참가자 2 : </p>
          <select class="doubt_select">
            <option selected disabled>------------</option>
            <option>노트주인</option>
            <option>추종자</option>
            <option>경찰총장</option>
            <option>보디가드</option>
            <option>방송인</option>
            <option>경찰</option>
          </select>
        </div>
        <div class="d-inline">
          <p class="d-inline">참가자 3 : </p>
          <select class="doubt_select">
            <option selected disabled>------------</option>
            <option>노트주인</option>
            <option>추종자</option>
            <option>경찰총장</option>
            <option>보디가드</option>
            <option>방송인</option>
            <option>경찰</option>
          </select>
        </div>
      </div>
      <div class="d-flex justify-content-around">
        <div class="d-inline">
          <p class="d-inline">참가자 4 : </p>
          <select class="doubt_select">
            <option selected disabled>------------</option>
            <option>노트주인</option>
            <option>추종자</option>
            <option>경찰총장</option>
            <option>보디가드</option>
            <option>방송인</option>
            <option>경찰</option>
          </select>
        </div>
        <div class="d-inline">
          <p class="d-inline">참가자 5 : </p>
          <select class="doubt_select">
            <option selected disabled>------------</option>
            <option>노트주인</option>
            <option>추종자</option>
            <option>경찰총장</option>
            <option>보디가드</option>
            <option>방송인</option>
            <option>경찰</option>
          </select>
        </div>
        <div class="d-inline">
          <p class="d-inline">참가자 6 : </p>
          <select class="doubt_select">
            <option selected disabled>------------</option>
            <option>노트주인</option>
            <option>추종자</option>
            <option>경찰총장</option>
            <option>보디가드</option>
            <option>방송인</option>
            <option>경찰</option>
          </select>
        </div>
      </div>
    </div>

    <!--정체 숨기기 횟수 / 스킬 횟수 / 검거권 수-->
    <div class="counts">
      <div>
        <!-- 정체 숨기기 횟수(미션 성공 횟수) -->
        <b-icon icon="eye-slash-fill" font-scale="2" variant="light"></b-icon><p>2회</p>
      </div>
      <div>
        <!-- 스킬 사용 가능 횟수(히든 미션 성공 횟수) -->
        <b-icon icon="lightning-fill" font-scale="2" variant="light"></b-icon><p>3회</p>
      </div>
      <!-- 검거권 수(경찰일때만)-->
      <!-- v-if=="경찰" 사용-->
      <div>
        <b-icon icon="person-bounding-box" font-scale="2" variant="light"></b-icon><p>1회</p>
      </div>
    </div>

    <!--미션 및 히든 미션 : mission-->
    <div class="mission">
      미션 및 히든 미션
    </div>

    <!--스킬사용 및 메모팝업 버튼 : buttons-->
    <div class="d-flex justify-content-around buttons">
      <!--능력사용-->
      <!--disabled 사용해서 skill이 1이상일때만 사용가능하도록-->
      <button class="skill_button">스킬 사용</button>
      <!--메모팝업-->
      <button class="memo_button">메모 하기</button>
    </div>
  </div>
</template>

<script>

export default {
  name : 'MainGame',
  data : function () {
    return {
    }
  },
  methods : {
    openmemo : function () {
    }
  }
}
</script>

<style scoped>
.buttons {
  position: fixed;
  top: 79%;
  right: 5%;
  width: 20%
}
.skill_button {
	background-color:#44c767;
	border-radius:28px;
	border:1px solid #18ab29;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
.skill_button:hover {
	background-color:#5cbf2a;
}
.skill_button:active {
	position:relative;
	top:1px;
}
.memo_button {
	background-color:transparent;
	border-radius:28px;
	border:1px solid #18ab29;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:17px;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
.memo_button:hover {
	background-color:transparent;
}
.memo_button:active {
	position:relative;
	top:1px;
}
.total_cam {
  position: fixed;
  top: 18%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 50%;
  height: 50%;
  flex-wrap: wrap;
}
.private_cam_4 {
  border-style: solid;
  border-color: white;
  width: 50%;
  height: 50%;
}
.private_cam_6 {
  border-style: solid;
  border-color: white;
  width: 33%;
  height: 50%;
}
.private_cam_8 {
  border-style: solid;
  border-color: white;
  width: 25%;
  height: 50%;
}
.timer {
  position: fixed;
  top: 15%;
  left: 57.5%;
  border-style: solid;
  border-color: white;
  width: 15%;
  height: 10%;
  display: flex;
  justify-content: center;
  align-items: center;
  /* text-align: center; */
}
.chat {
  position: fixed;
  top: 15%;
  right: 5%;
  border-style: solid;
  border-color: white;
  width: 20%;
  height: 60%;
}
.chat_list {
  position: absolute;
  top: 5%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 90%;
  height: 70%
}
.chat_input {
  position: absolute;
  top: 80%;
  left: 5%;
  border-style: solid;
  border-color: white;
  background-color: transparent;
  width: 90%;
  height: 15%;
}
.mission {
  position: fixed;
  top: 60%;
  left: 57.5%;
  border-style: solid;
  border-color: white;
  width: 15%;
  height: 27%;
}
.explain {
  position: fixed;
  top: 29%;
  left: 57.5%;
  border-style: solid;
  border-color: white;
  width: 15%;
  height: 27%;
  /* text-align: center; */
}
.dotted_line {
  border-bottom-style: dotted;
  border-color: white;
}
.doubt {
  position: fixed;
  top: 72%;
  left: 5%;
  border-style: solid;
  border-color: white;
  width: 38%;
  height: 15%;
  flex-wrap: wrap;
}
.doubt_select {
  background-color: transparent;
  background: black;
  color: white;
  border-radius: 8px;
}
.counts {
  position: fixed;
  top: 72%;
  left: 45%;
  border-style: solid;
  border-color: white;
  width: 10%;
  height: 15%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.counts_box {
  display: inline;

}
.explain_job {
  font-size: 120%;
  height: 15%;
}
.explain_job_description {
  height: 30%;
}
.explain_skill {
  height: 25%;
}
.explain_victory {
  height: 30%;
}
</style>