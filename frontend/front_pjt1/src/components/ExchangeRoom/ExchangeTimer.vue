<template>
	<div>
    <circular-count-down-timer
      :circles="exchangeTimer.circles"
      :interval="exchangeTimer.interval"
      :main-circle-id="exchangeTimer.mainCircleId || '1'"
      :size="exchangeTimer.size"
      :container-classes="exchangeTimer.containerClasses"
      :circle-classes="exchangeTimer.circleClasses"
      :stop-conditions="exchangeTimer.stopConditions"
      :trigger-update="exchangeTimer.triggerUpdate"
      :stroke-width="exchangeTimer.strokeWidth"
      :stroke-color="exchangeTimer.strokeColor"
      :underneath-stroke-color="exchangeTimer.underneathStrokeColor"
      :fill-color="exchangeTimer.fillColor"
      :value-font-size="exchangeTimer.valueFontSize"
      :label-font-size="exchangeTimer.labelFontSize"
      :label-position="exchangeTimer.labelPosition"
    />
	</div>
</template>

<script>
export default {
	name: 'ExchangeTimer',
	data(){
		return {
      exchangeTimer:{
        title: 'Full configuration',
        containerClasses: [],
        circleClasses: [],
        interval: 1000,
        mainCircleId: '1',
        stopConditions: {},
        triggerUpdate: false,
        circles: [
          {
            id: '1',
            classList: [ 'item' ],
            startValue: -2,
            value: 1,
            size: 200,
            strokeWidth: 20,
            strokeColor: '#db47a0',
            underneathStrokeColor: 'orange',
            fillColor: '#fff0a0',
            valueFontSize: 30,
            labelFontSize: 30,
            steps: 10,
            stepLength: 1,
            label: 'label',
            labelPosition: 'bottom'
          }
        ]
      }
    }
	},
};
</script>

<style>
</style>