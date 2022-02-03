<template>
    <div>
        <label>Nickname</label>
        <input
            type="text"
            v-model="nickname"
            class="form-control"
            required
        >
        <button class="btn btn-lg btn-success" @click="nicknameUpdate()">참가</button>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

const gameStore = 'gameStore';

export default {
	name: 'Entrance',
    data () {
        return{
            nickname:''
        }
    },
	methods:  {
        ...mapActions(gameStore, ['nicknameUpdate'])
    }
};
</script>
