<template>
    <div>
		<div class="row count m-1">
			<p class="col-2 count">{{ready}}</p>
			<div class="col count">
				{{ clientData }}
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'Ready',

	props: {
		streamManager: Object,
	},
	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
		ready () {
			return this.streamManager.ready
		}
	},

	methods: {
		getConnectionData () {
			// console.log(this.streamManager.stream)
			console.log('스트림메니져 레디에서 출력')
			console.log(this.streamManager)
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>