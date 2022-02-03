<template>
	<div class="row count m-1">
		<div class="col-8 count">
            {{job}}
        </div>
		<div class="col-4 count">명</div>
	</div>
</template>

<script>
export default {
	name: 'JobSelect',

	props: {
		job:String
	},

};
</script>
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>