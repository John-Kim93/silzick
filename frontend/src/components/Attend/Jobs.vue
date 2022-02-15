<template>
	<div class="row m-1">
		<div id="base-border" class="col-8">
			{{job.jobName}}
		</div>
    <div class="col-2">
      <button @click="plus" id="btn-color" class="btn btn-size">+</button>
    </div>
    <div class="col-2">
      <button @click="minus" id="btn-color" class="btn btn-size">-</button>
    </div>
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
					gameStatus: 1,
					jobName: this.job.jobName,
					count: this.job.count + 1
				}
				this.changeJobCount(jobProps)
			}
		},
		minus () {
			if (this.job.isChange && this.job.count > this.job.minCount) {
				const jobProps = {
					gameStatus: 1,
					jobName: this.job.jobName,
					count: this.job.count - 1
				}
				this.changeJobCount(jobProps)
			}
		}
	}
};
</script>
<style scoped>
.btn-size {
  padding: 0.3rem 0.6rem 0.3rem 0.6rem;
}
</style>