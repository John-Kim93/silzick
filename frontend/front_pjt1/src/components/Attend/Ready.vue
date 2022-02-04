<template>
    <div>
		<div class="row count m-1">
			<img class="col-1 count">
			<div class="col count">
				{{ clientData }}
			</div>
		</div>
		<p style='color:#ff0a00'>{{publisher}}</p>
		<p>test</p>
	</div>
</template>

<script>
import { mapState } from 'vuex'

const gameStore = 'gameStore'

export default {
	name: 'Ready',

	props: {
		streamManager: Object,
	},
	computed: {
		...mapState(gameStore, ['publisher']),
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},
	watch: {
		streamManager (pre, next) {
			console.log('왓치 퍼블리셔')
			console.log(pre)
			console.log(next)
			console.log(this.streamManager)
		}
	},
	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},	
	updated () {
		console.log('스트림메니져 출력')
		console.log(this.streamManager)
	}
};
</script>
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>