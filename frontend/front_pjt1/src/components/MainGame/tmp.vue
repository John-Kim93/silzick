    <!--정체 숨기기 횟수 / 스킬 횟수 / 검거권 수-->
    <div class="">
      <div>
        <!-- 정체 숨기기 횟수(미션 성공 횟수) -->
        <b-icon icon="eye-slash-fill" font-scale="2" variant="light"></b-icon><p>{{missionSuccessCount}}</p>
      </div>
      <div v-if="myJob=='POLICE'">
        <!-- 스킬 사용 가능 횟수(히든 미션 성공 횟수) -->
        <b-icon icon="lightning-fill" font-scale="2" variant="light"></b-icon><p>{{numberOfSkillUse/2}}</p>
      </div>
      <!-- 검거권 수(경찰일때만)-->
      <!-- v-if=="경찰" 사용-->
      <div v-else>
        <b-icon icon="lightning-fill" font-scale="2" variant="light"></b-icon><p>{{numberOfSkillUse}}</p>
      </div>
    </div>