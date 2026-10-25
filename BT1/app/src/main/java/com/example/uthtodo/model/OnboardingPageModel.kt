package com.example.uthtodo.model

enum class OnboardingCardType {
    TASK_LIST,     // Màn hình 1a
    CATEGORIES,    // Màn hình 1b
    NOTIFICATION   // Màn hình 1c
}

data class OnboardingPageModel(
    val stepIndex: Int,
    val title: String,
    val description: String,
    val buttonText: String,
    val cardType: OnboardingCardType,
    val nextRoute: String,
    val isLastPage: Boolean = false
) {
    companion object {
        fun getPageByStep(stepIndex: Int): OnboardingPageModel {
            return getPages().getOrElse(stepIndex) { getPages().first() }
        }

        fun getPages(): List<OnboardingPageModel> = listOf(
            OnboardingPageModel(
                stepIndex = 0,
                title = "Mọi việc, gọn trong một chỗ",
                description = "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
                buttonText = "Tiếp tục",
                cardType = OnboardingCardType.TASK_LIST,
                nextRoute = "onboarding_1b"
            ),
            OnboardingPageModel(
                stepIndex = 1,
                title = "Chia theo Học tập và Cuộc sống",
                description = "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
                buttonText = "Tiếp tục",
                cardType = OnboardingCardType.CATEGORIES,
                nextRoute = "onboarding_1c"
            ),
            OnboardingPageModel(
                stepIndex = 2,
                title = "Nhắc đúng lúc",
                description = "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
                buttonText = "Bắt đầu ngay",
                cardType = OnboardingCardType.NOTIFICATION,
                nextRoute = "start",
                isLastPage = true
            )
        )
    }
}

data class StartPageModel(
    val titleBold: String = "UTH",
    val titleRegular: String = " Todo",
    val subtitle: String = "Việc học, việc nhà — gọn gàng mỗi ngày",
    val version: String = "Phiên bản 2.1.0"
)
