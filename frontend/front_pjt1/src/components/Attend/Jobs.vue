<template>
	<div class="row count m-1">
		<div class="col-8 count">
            {{job}}
        </div>
		<button @click="addJob" class="col-2">+</button>
		<button @click="removeJob" class="col-2">-</button>
	</div>
</template>

<script>
export default {
	name: 'Jobs',

	props: {
		job:String
	},
	methods: {
		
	}
};
</script>
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>