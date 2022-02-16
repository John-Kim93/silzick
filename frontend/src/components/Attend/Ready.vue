<template>
  <div class="row m-1">
    <div id="base-border" class="col-8">
      {{ clientData }}
    </div>
    <div
      v-if="ready"
      class="col-2 d-inline-flex ready_check"
      style="color:#30475E;"
    >
      <b-icon icon="check2-circle" font-scale="2"></b-icon>
    </div>
    <div
      v-else
      class="col-2 d-inline-flex ready_check"
      style="color:#222831;"
    >
      <b-icon icon="check2-circle" font-scale="2"></b-icon>
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
			console.log('스트림메니져 레디에서 출력')
			console.log(this.streamManager)
			const { connection } = this.streamManager.stream;
			console.log(connection)
			return JSON.parse(connection.data);
		},
	},
};
</script>
<style scoped>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>