<template>
	<div class="row count m-1">
		<div class="col-8 count">
			{{job.jobName}}
		</div>
		<div class="col-4 count">{{job.count}}명</div>
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
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>