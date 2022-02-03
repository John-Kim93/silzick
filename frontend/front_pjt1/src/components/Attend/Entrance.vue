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
export default {
	name: 'Entrance',
    data:function(){
        return{
            nickname:''
        }
    },
	methods:  {
		nicknameUpdate(){
            this.$emit('nick-name-update',this.nickname)
        }
	},
};
</script>
