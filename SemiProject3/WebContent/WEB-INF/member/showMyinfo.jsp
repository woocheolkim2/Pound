<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
td{
	line-height: 200%;
}
.bold{
	font-weight: bold;
}
#infoTable{
	 text-align:center; 
	 font-size: 1em;
	 width: 80%;
	 border-top: 3px double gray; 
	 border-bottom: 3px double gray; 
	 margin: 2% 0;
}
</style>
<div class="content" style="width: 75%; padding-left: 50px;">
	<div class="row">
        <div class="col-md-12">
          <article>
             <div class="row">
				<div class="col-md-8 col-md-offset-2" id="info">
					<h3 style="margin-left: 2%;"><span style="font-weight: bold;">${loginMember.username}</span>님의 정보</h3>
					<table id="infoTable">
						<tr><td class="bold"> 아이디 </td><td>${loginMember.userid}</td></tr>
						<tr><td class="bold"> 이메일 </td><td>${loginMember.email}</td></tr>
						<tr><td class="bold"> 연락처 </td><td>${loginMember.allHp}</td></tr>
						<tr><td class="bold"> 우편번호 </td><td>${loginMember.allPost}</td></tr>
						<tr><td class="bold"> 주소 </td><td>${loginMember.allAddr}</td></tr>
						<tr><td class="bold"> 생년월일 </td><td>${membervo.birthday}</td></tr>
						<tr><td class="bold"> 나이 </td><td>${membervo.showAge}세</td></tr>
						<tr><td class="bold"> 성별 </td><td>${loginMember.showGender}</td></tr>
						<tr><td class="bold"> 가입일자 </td><td>${membervo.getRegisterdate()}</td></tr>
						<tr><td class="bold"> 포인트 </td><td>${membervo.point } POINT</td></tr>
					</table>
				</div>
			</div>
           </article>
          <div class="row">
            <article>
              <h1>Some More Content</h1>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. </p>
				
				<p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. <b>Lorem ipsum dolor sit amet, consectetur adipiscing elit</b>. Curabitur sodales ligula in libero. <i>Lorem ipsum dolor sit amet, consectetur adipiscing elit</i>. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. <b>Mauris massa</b>. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. </p>
				
				<p>Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. <b>Maecenas mattis</b>. Nam nec ante. <b>Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa</b>. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. <i>Aenean quam</i>. Nunc feugiat mi a tellus consequat imperdiet. <b>Fusce ac turpis quis ligula lacinia aliquet</b>. Vestibulum sapien. Proin quam. <b>Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis</b>. Etiam ultrices. </p>
				
				<p>Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulum tincidunt malesuada tellus. </p>
				
				<p>Ut ultrices ultrices enim. <b>Suspendisse in justo eu magna luctus suscipit</b>. Curabitur sit amet mauris. Morbi in dui quis est pulvinar ullamcorper. <i>Quisque volutpat condimentum velit</i>. Nulla facilisi. Integer lacinia sollicitudin massa. Cras metus. Sed aliquet risus a tortor. Integer id quam. Morbi mi. Quisque nisl felis, venenatis tristique, dignissim in, ultrices sit amet, augue. Proin sodales libero eget ante. Nulla quam. </p>

            </article>
          </div>
        </div>
    </div>
  </div>