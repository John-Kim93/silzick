<template>
    <div>
		<div class="row count m-1">
			<img class="col-1 count">
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
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
	created () {
		console.log('@@@@@@@@')
		console.log(this.streamManager)
		console.log('@@@@@@@@@')
	}
};
</script>
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>