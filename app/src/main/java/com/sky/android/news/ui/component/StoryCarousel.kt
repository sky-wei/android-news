/*
 * Copyright (c) 2024 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.news.ui.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sky.android.news.Constant
import com.sky.android.news.R
import com.sky.android.news.data.model.story.TopStoryItemModel
import com.sky.android.news.ext.carouselTransition
import com.sky.android.news.ui.theme.ColorTranslucentBlack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by sky on 11/15/24.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryCarousel(
    topStories: List<TopStoryItemModel>,
    autoScrollDuration: Long = Constant.Time.CAROUSEL_AUTO_SCROLL_TIMER,
    onItemClicked: (TopStoryItemModel) -> Unit
) {
    val pagerState = rememberPagerState { topStories.size }

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            if (pageCount > 0) {
                var currentPageKey by remember { mutableIntStateOf(0) }
                LaunchedEffect(key1 = currentPageKey) {
                    launch {
                        delay(timeMillis = autoScrollDuration)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(
                                durationMillis = Constant.Time.ANIM_TIME_LONG
                            )
                        )
                        currentPageKey = nextPage
                    }
                }
            }
        }
    }

    Box {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 15.dp),
            pageSpacing = 16.dp
        ) { page: Int ->

            val item = topStories[page]

            Card(
                onClick = { onItemClicked(item) },
                modifier = Modifier.carouselTransition(
                    page,
                    pagerState
                )
            ) {
                CarouselBox(
                    item = item
                )
            }
        }

        StoryIndicators(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            selectedColor = Color.White,
            unselectedColor = Color.Gray,
            pagerState = pagerState
        )
    }
}

@Composable
private fun CarouselBox(
    item: TopStoryItemModel
) {
    Box {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.ic_load_placeholder),
            error = painterResource(R.drawable.ic_load_error),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        val gradient = remember {
            Brush.verticalGradient(
                listOf(
                    Color.Transparent,
                    ColorTranslucentBlack
                )
            )
        }

        Text(
            text = item.title,
            color = Color.White,
            style = TextStyle(
                fontSize = 14.sp
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(gradient)
                .padding(horizontal = 6.dp, vertical = 12.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoryIndicators(
    modifier: Modifier,
    selectedColor: Color,
    unselectedColor: Color,
    pagerState: PagerState
) {
    Row(
        modifier = modifier
    ) {
        repeat(pagerState.pageCount) { index ->

            val color = if (pagerState.currentPage == index)
                selectedColor
            else
                unselectedColor

            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 4.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}