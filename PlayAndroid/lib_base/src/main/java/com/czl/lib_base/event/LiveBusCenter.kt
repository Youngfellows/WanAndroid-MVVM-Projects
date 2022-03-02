package com.czl.lib_base.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.czl.lib_base.data.bean.TodoBean
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author Alwyn
 * @Date 2020/10/16
 * @Description 事件分发中心管理
 * 可通过Kotlin的命名可选参数特性 一个方法即可判断 发送/接收
 * sticky : post/observeSticky
 */
object LiveBusCenter {

//    fun handleMainEvent(
//        value: String? = null,
//        owner: LifecycleOwner? = null,
//        observer:Observer<MainEvent>?=null
//    ) {
//        if (null == owner && value != null) {
//            LiveEventBus.get(MainEvent::class.java).post(MainEvent(value))
//            return
//        }
//        owner?.let{
//            observer?.apply {
//                LiveEventBus.get(MainEvent::class.java).observe(it, this)
//            }
//        }
//    }

    fun postMainEvent(value: String?) {
        LiveEventBus.get(MainEvent::class.java).post(MainEvent(value))
    }

    fun observeMainEvent(owner: LifecycleOwner, func: (t: MainEvent) -> Unit) {
        LiveEventBus.get(MainEvent::class.java).observe(owner, Observer(func))
    }

//    fun postTokenExpiredEvent(value: String?) {
//        LiveEventBus.get(TokenExpiredEvent::class.java).post(TokenExpiredEvent(value))
//    }
//
//    fun observeTokenExpiredEvent(owner: LifecycleOwner, observer: Observer<TokenExpiredEvent>) {
//        LiveEventBus.get(TokenExpiredEvent::class.java).observe(owner, observer)
//    }

    fun postRegisterSuccessEvent(account: String?, pwd: String?) {
        LiveEventBus.get(RegisterSuccessEvent::class.java).post(RegisterSuccessEvent(account, pwd))
    }

    fun observeRegisterSuccessEvent(
        owner: LifecycleOwner,
        func: (t: RegisterSuccessEvent) -> Unit
    ) {
        LiveEventBus.get(RegisterSuccessEvent::class.java).observe(owner, Observer(func))
    }

    fun postSearchHistoryEvent() {
        LiveEventBus.get(SearchHistoryEvent::class.java).post(SearchHistoryEvent(0))
    }

    fun observeSearchHistoryEvent(owner: LifecycleOwner, func: (t: SearchHistoryEvent) -> Unit) {
        LiveEventBus.get(SearchHistoryEvent::class.java).observe(owner, Observer(func))
    }

    fun postLogoutEvent() {
        LiveEventBus.get(LogoutEvent::class.java).post(LogoutEvent(0))
    }

    fun observeLogoutEvent(owner: LifecycleOwner, func: (t: LogoutEvent) -> Unit) {
        LiveEventBus.get(LogoutEvent::class.java).observe(owner, Observer(func))
    }

    fun postLoginSuccessEvent() {
        LiveEventBus.get(LoginSuccessEvent::class.java).post(LoginSuccessEvent(0))
    }

    fun observeLoginSuccessEvent(owner: LifecycleOwner, func: (t: LoginSuccessEvent) -> Unit) {
        LiveEventBus.get(LoginSuccessEvent::class.java).observe(owner, Observer(func))
    }

    fun postRefreshUserFmEvent() {
        LiveEventBus.get(RefreshUserFmEvent::class.java).post(RefreshUserFmEvent(0))
    }

    fun observeRefreshUserFmEvent(owner: LifecycleOwner, func: (t: RefreshUserFmEvent) -> Unit) {
        LiveEventBus.get(RefreshUserFmEvent::class.java).observe(owner, Observer(func))
    }

    fun postRefreshWebListEvent() {
        LiveEventBus.get(RefreshWebListEvent::class.java).post(RefreshWebListEvent(0))
    }

    fun observeRefreshWebListEvent(owner: LifecycleOwner, func: (t: RefreshWebListEvent) -> Unit) {
        LiveEventBus.get(RefreshWebListEvent::class.java).observe(owner, Observer(func))
    }

    fun postCollectStateEvent(originId: Int) {
        LiveEventBus.get(RefreshCollectStateEvent::class.java)
            .post(RefreshCollectStateEvent(originId))
    }

    fun observeCollectStateEvent(
        owner: LifecycleOwner,
        func: (t: RefreshCollectStateEvent) -> Unit
    ) {
        LiveEventBus.get(RefreshCollectStateEvent::class.java).observe(owner, Observer(func))
    }

    fun postSwitchReadHistoryEvent(checked:Boolean) {
        LiveEventBus.get(SwitchReadHistoryEvent::class.java).post(SwitchReadHistoryEvent(checked))
    }

    fun observeReadHistoryEvent(owner: LifecycleOwner, func: (t: SwitchReadHistoryEvent) -> Unit) {
        LiveEventBus.get(SwitchReadHistoryEvent::class.java).observe(owner, Observer(func))
    }

    fun postTodoListRefreshEvent(todoInfo:TodoBean.Data){
        LiveEventBus.get(TodoListRefreshEvent::class.java).post(TodoListRefreshEvent(0,todoInfo))
    }
    fun observeTodoListRefreshEvent(owner: LifecycleOwner, func: (t: TodoListRefreshEvent) -> Unit){
        LiveEventBus.get(TodoListRefreshEvent::class.java).observe(owner, Observer(func))
    }

}