<template>
	<div class="base-charts">
		<!-- <el-row>
			<el-col :span="12" class="list-item">
				<div class="grid-content bg-purple">
					<Dotted/>
				</div>
			</el-col>
			<el-col :span="12" class="list-item">
				<div class="grid-content bg-purple-light">
					<Stacked/>
				</div>
			</el-col>
			<el-col :span="12" class="list-item">
				<h1>年龄分布</h1>
				<div class="grid-content bg-purple-light">
					<ageAnalysis/>
				</div>
			</el-col>
		</el-row> -->
		<el-col :span="12" class="list-item">
				<h1>年龄分布</h1>
				<div class="grid-content bg-purple-light">
					<ageAnalysis/>
				</div>
			</el-col>
	</div>
</template>

<script>
	import Stacked from './echarts/Stacked'
	import Dotted from './echarts/Dotted'
	import ageAnalysis from './echarts/ageAnalysis'
	export default {
	  name: 'Nofind',
		data(){
			return {
				
			}
		},
		components:{
			Stacked,
			Dotted,
			ageAnalysis
		}
	}
</script>
<style scoped>
.base-charts {
	width: 100%;
}
.list-item {
	padding-left: 20px;
	padding-right: 20px;
}
</style>
