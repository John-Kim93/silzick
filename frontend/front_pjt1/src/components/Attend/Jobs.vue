<template>
	<div class="row count m-1">
		<div class="col-8 count">
			{{job.jobName}}
		</div>
		<button @click="plus" class="col-2">+</button>
		<button @click="minus" class="col-2">-</button>
	</div>
</template>

<script>
import { mapActions } from 'vuex'

const gameStore = 'gameStore'

export default {
	name: 'Jobs',

	props: {
		job:Object
	},
	methods: {
		...mapActions(gameStore, ['changeJobCount']),
		plus () {
			if (this.job.isChange && this.job.count < this.job.maxCount) {
				const jobProps = {
					jobName: this.job.jobName,
					count: this.job.count + 1
				}
				this.changeJobCount(jobProps)
			}
		},
		minus () {
			if (this.job.isChange && this.job.count > 0) {
				const jobProps = {
					jobName: this.job.jobName,
					count: this.job.count - 1
				}
				this.changeJobCount(jobProps)
			}
		}
	}
};
</script>
<style>
	.count{
		border-radius: 3%;
		border: 1px solid black;
	}
</style>