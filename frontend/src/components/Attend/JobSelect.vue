<template>
	<div class="row m-1">
		<div id="base-border" class="col-8">
			{{job.jobName}}
		</div>
		<div class="offset-1 col-3">{{job.count}}명</div>
	</div>
</template>

<script>
export default {
	name: 'JobSelect',

	props: {
		job:Object
	},
};
</script>
<style scoped>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>