package com.laya.advice.model;

/**
 * 问题数据库模型
 * @author yc
 *
 */
public class Question {

	      private Integer id;          //标识
	      private String userId;       //用户id
	      private String memberId;     //39用户成员id
	      private String tid;         //问题id
	      private String roleId;       //拉雅用户成员id
	      private String createTime;    //创建时间
	      private Integer replyCount;  //回复次数
	      private String title;        //问题名称
	      private String images;       //病例图片
	      
	      
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitile(String title) {
			this.title = title;
		}
		public Integer getReplyCount() {
			return replyCount;
		}
		public void setReplyCount(Integer replyCount) {
			this.replyCount = replyCount;
		}
		@Override
		public String toString() {
			return "Question [id=" + id + ", userId=" + userId + ", memberId="
					+ memberId + ", tid=" + tid + ", roleId=" + roleId
					+ ", createTime=" + createTime + ", titile=" + title
					+ ", replyCount=" + replyCount + "]";
		}
		public String getImages() {
			return images;
		}
		public void setImages(String images) {
			this.images = images;
		}
	

}
