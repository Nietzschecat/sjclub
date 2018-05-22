[#escape x as x?html]
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="registerModal">
  <div class="modal-dialog">
    <div class="modal-content">
    	<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">注册</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" role="form">
			<!-- account -->
			<div class="form-group has-feedback" id="reg-account">
		    	<label class="col-sm-2 control-label">账号</label>
		    	<div class="col-sm-10">
		     		<input type="loginName" class="form-control" placeholder="请输入你的账号">
		     		<span class="glyphicon form-control-feedback" id="reg-account-gly"></span>
			      	<span class="help-block" id="reg-account-tip"></span>
		    	</div>
		    </div>
			<!-- name -->

			<!-- name -->
			<div class="form-group has-feedback" id="reg-name">
				<label class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="userName" class="form-control" placeholder="输入您喜欢的姓名">
					<span class="glyphicon form-control-feedback" id="reg-name-gly"></span>
					<span class="help-block" id="reg-name-tip"></span>
				</div>
			</div>
			<div class="form-group has-feedback" id="reg-trueName">
				<label class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					男<input type="radio" name="sex" value="0" checked="checked" />
					女<input type="radio" name="sex" value="1" />
					<span class="glyphicon form-control-feedback" id="reg-trueName-gly"></span>
					<span class="help-block" id="reg-trueName-tip"></span>
				</div>
			</div>
			<div class="form-group has-feedback" id="reg-number">
				<label class="col-sm-2 control-label">学号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" placeholder="输入您学号">
					<span class="glyphicon form-control-feedback" id="reg-number-gly"></span>
					<span class="help-block" id="reg-number-tip"></span>
				</div>
			</div>
			<!-- password -->
			<div class="form-group has-feedback" id="reg-password">
			    <label class="col-sm-2 control-label">密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" placeholder="输入密码">
					<span class="glyphicon form-control-feedback" id="reg-password-gly"></span>
			     	<span class="help-block" id="reg-password-tip"></span>
				</div>
		  	</div>
			<!-- repassword -->
			<div class="form-group has-feedback" id="reg-repassword">
		    <label class="col-sm-2 control-label">确认密码</label>
		    <div class="col-sm-10">
		      <input type="password" class="form-control" placeholder="再输入一次密码">
		      <span class="glyphicon form-control-feedback" id="reg-repassword-gly"></span>
			  <span class="help-block" id="reg-repassword-tip"></span>
		    </div>
		  </div>
		</form>
      </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-success" id="reg-submit">注册</button>
      </div>
    </div>
  </div>
</div>
[/#escape]