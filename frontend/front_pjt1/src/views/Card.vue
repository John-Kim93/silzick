<template>
    <div>
    <h1>명함교환</h1>    
        <div class="container">
            <div>
                <img src="" alt="캠">
            </div>
            <p>선택한 직업:</p>
            <div>
                <b-form-select v-model="selected" :options="options"></b-form-select>
                <div class="mt-3">Selected: <strong>{{ selected }}</strong></div>
            </div>
            <button>확정</button>
        </div>
        <div class="container">
            <div>
                <img src="" alt="캠">
            </div>
            <p>선택한 직업:</p>
            <div>
                <b-form-select v-model="selected" :options="options"></b-form-select>
                <div class="mt-3">Selected: <strong>{{ selected }}</strong></div>
            </div>
            <button>확정</button>
        </div>
        <p>timer</p>
        {{ timerCount }}
        <b-form-textarea
        id="textarea"
        v-model="text"
        placeholder="Enter something..."
        rows="3"
        max-rows="6"
        ></b-form-textarea>
        <pre class="mt-3 mb-0">{{ text }}</pre>
        <input type="text">
    </div>
</template>

<script>
    export default {
        data() {
            return {
                timerCount:30,
                text: '',
                selected: null,
                options: [
                    { value: null, text: '노트주인' },
                    { value: 'a', text: '추종자' },
                    { value: 'b', text: '경찰' },
                ]
            }
        },
        watch: {
            timerCount: {
                handler(value) {
                    if (value > 0) {
                        setTimeout(() => {
                            this.timerCount--;
                        }, 1000);
                    }
                },
                immediate: true
            }

        }
    }
</script>

<style>
.container{
    display: inline-flex;
    border: 1px, solid, black;
}
</style>