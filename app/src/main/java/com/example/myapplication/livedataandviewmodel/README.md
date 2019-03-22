// 获取 User 的数据后
mTvUser.setText(user.toString());
这样做的一个问题，如果获取或者修改 User 的来源不止一处，那么需要在多个地方更新 TextView，并且如果在多处 UI 用到了 User，那么也需要在多处更新。
使用 LiveData 与 ViewModel 的组合，将LiveData 持有 User 实体，作为一个被观察者，当 User 改变时，所有使用 User 的地方自动 change。构建一个 UserViewModel 如下：
