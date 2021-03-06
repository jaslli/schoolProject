<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <a href="/course" title class="c-999 fsize14">{{courseWebVo.subjectLevelOne}}</a>
        \
        <span class="c-333 fsize14">{{courseWebVo.subjectLevelTwo}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px">
          <section class="p-h-video-box" id="videoPlay">
            <img :src="courseWebVo.cover" :alt="courseWebVo.title" class="dis c-v-pic"/>
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseWebVo.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size: 24px">￥{{courseWebVo.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseWebVo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#">收藏</a>
              </span>
            </section>
            <section class="c-attr-mt">
              <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol clearfix">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{courseWebVo.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{courseWebVo.lessonNum}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{courseWebVo.viewCount}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p>{{courseWebVo.description}}</p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li class="lh-menu-stair" v-for="chapter in chapterVoList" :key="chapter.id">
                            <a href="javascript: void(0)" title="chapter.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                            </a>
                            <ol class="lh-menu-ol" style="display: block">
                              <li class="lh-menu-second ml30" v-for="video in chapter.children" :key="video.id">
                                <a :href="'/player/'+video.videoSourceId" title target="_blank">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="courseWebVo.avatar" width="50" height="50" alt/>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{courseWebVo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseWebVo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
    <!-- 课程评论 -->
    <div class="mt50 commentHtml"><div>
          <h6 class="c-c-content c-infor-title" id="i-art-comment">
            <span class="commentTitle">课程评论</span>
          </h6>
          <section class="lh-bj-list pr mt20 replyhtml">
            <ul>
              <li class="unBr">
                <aside class="noter-pic">
                  <img width="50" height="50" class="picImg" :src="comment.avatar">
                  </aside>
                <div class="of">
                  <section class="n-reply-wrap">
                    <fieldset>
                      <textarea name="" v-model="comment.content" placeholder="输入您要评论的文字" id="commentContent"></textarea>
                    </fieldset>
                    <p class="of mt5 tar pl10 pr10">
                      <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                      <input type="button" @click="addComment()" value="回复" class="lh-reply-btn">
                    </p>
                  </section>
                </div>
              </li>
            </ul>
          </section>
          <section class="">
              <section class="question-list lh-bj-list pr">
                <ul class="pr10">
                  <li v-for="(item,index) in list" v-bind:key="index">
                      <aside class="noter-pic">
                        <img width="50" height="50" class="picImg" :src="item.avatar">
                        </aside>
                      <div class="of">
                        <span class="fl">
                        <font class="fsize12 c-blue">
                          {{item.nickname}}</font>
                        <font class="fsize12 c-999 ml5">评论：</font>
                        </span>
                      </div>
                      <div class="noter-txt mt5">
                        <p>{{item.content}}</p>
                      </div>
                      <div class="of mt5">
                        <div style="text-align: right;">
                          <input type="button" @click="deleteComment(item.id)" value="删除" class="lh-reply-btn">
                        </div>
                      </div>

                      <div class="of mt5">
                        <span class="fr"><font class="fsize12 c-999 ml5">{{item.gmtCreate}}</font></span>
                      </div>
                    </li>
                  </ul>
              </section>
            </section>
            <!-- 分页 开始 -->
            <el-pagination
              background
              layout="total,prev, pager, next,jumper"
              :current-page="page"
              :page-size="limit"
              :total="total"
              style="padding: 30px 0; text-align: center;"
              @current-change="getList"
            />
            <!-- 分页 结束 -->
          </div>
      </div>
  </div>
</template>
<script>
import indexApi from '@/api/index'
import cookie from 'js-cookie'
export default {
  asyncData({ params, error }) {
    return indexApi.getCourseInfo(params.id)
     .then(response => {
       return {
         courseWebVo: response.data.data.courseWebVo,
         chapterVoList: response.data.data.chapterVoList,
         courseId: params.id,
         teacherId: response.data.data.courseWebVo.teacherId
       }
     })
  },
  data() {
    return {
      islogin: false, // 是否登陆
      comment: {
        content:'',
        courseId:this.courseId,
        nickname:'',
        avatar:'',
        teacherId: this.teacherId,
        memberId: ''
      },
      list: null, // 查询之后返回的结果
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 5, // 每页记录数
    }
  },
  created() {
    this.getList()
    this.getInfo()
  },
  methods: {
    // 分页查询课程评论信息
    getList(page = 1) {
      this.page = page
      indexApi.getComment(this.courseId,this.page,this.limit)
        .then(response => {
            this.list = response.data.data.rows
            this.total = response.data.data.total
        })
    },
    // 添加评论
    addComment() {
      if(this.islogin) {
        if(this.comment.content === '') {
          this.$message({
            type: 'error',
            message: '请输入评论'
          });
        } else {
          indexApi.addComment(this.comment).then(response => {
            this.$message({
            type: 'success',
            message: '评论成功'
            })
            this.getList()
          })
        }
      } else {
        this.$message({
            type: 'error',
            message: '请先登录️再进行下一步操作'
          });
          //vue路由跳转
          this.$router.push({
            path: '/login'
          })
      }
    },
    // 从cookie获取用户信息
    getInfo() {
      // 将JSON字符串转换成对象
      let userStr = cookie.get('school_ucenter')
      if(userStr) {
        let loginInfo = JSON.parse(userStr)
        this.comment.avatar = loginInfo.avatar
        this.comment.nickname = loginInfo.nickname
        this.comment.teacherId = this.teacherId
        this.comment.memberId = loginInfo.id
        this.comment.courseId = this.courseId
        this.islogin = true
      }
    }
  }
}
</script>