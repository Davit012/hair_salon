$('.featured-course').owlCarousel({
    stagePadding: 0,
    loop: false,
    margin: 20,
    nav: true,
    navigation: true,
    dots: true,
    navText: [`<span class='prev-slide d-flex align-items-center justify-content-center'>
<svg width="26" height="24" >
    <use xlink:href="../sprite.svg#prevArrow"></use>
    </svg></span>`, `<span class='next-slide d-flex align-items-center justify-content-center'>
<svg width="26" height="24" >
    <use xlink:href="../sprite.svg#nextArrow"></use>
    </svg>
</span>`],
    responsive: {
        0: {
            dots:false,
            items: 1.5,
            nav: false,
            navigation: false,
        },
        600: {
            dots:true,
            stagePadding: 0,
            items: 2,
            nav: true,
            navigation: true,
        },
        768: {
            items: 3
        },
        1200: {
            items: 4
        }
    }
});
$('.our-mentors').owlCarousel({
    stagePadding: 0,
    loop: false,
    margin: 22,
    nav: true,
    navigation: true,
    dots: false,
    navText: [`<span class='prev-slide d-flex align-items-center justify-content-center'>
<svg width="26" height="24" >
    <use xlink:href="../sprite.svg#prevArrow"></use>
    </svg></span>`, `<span class='next-slide d-flex align-items-center justify-content-center'>
<svg width="26" height="24" >
    <use xlink:href="../sprite.svg#nextArrow"></use>
    </svg>
</span>`],
    responsive: {
        0: {
            items: 1.5,
            nav: false,
            navigation: false,
            dots:false,
        },
        600: {
            dots:true,
            nav: true,
            navigation: true,
            stagePadding: 0,
            items: 2
        },
        768: {
            items: 3
        },
        1200: {
            items: 3
        }
    }
});

$('.students-carousel').owlCarousel({
    stagePadding: 0,
    loop: false,
    margin: 62,
    nav: true,
    navigation: false,
    dots: false,
    navText: false,
    responsive: {
        0: {
            items: 1,
            dots: true,
            navigation: false,
        },
        768: {
            items: 2,
            dots: true,
            navigation: false,
            margin: 30,
        },
        1200: {
            navText: [`<span class='d-flex align-items-center justify-content-center'>
<svg width="36" height="55" >
    <use xlink:href="../sprite.svg#prevOnlyArrow"></use>
    </svg></span>`, `<span class='d-flex align-items-center justify-content-center'>
<svg width="36" height="55" >
    <use xlink:href="../sprite.svg#nextOnlyArrow"></use>
    </svg>
</span>`],
            items: 2,
            dots: false,
            navigation: true,
        }
    }
});

$('.partners-carousel').owlCarousel({
    stagePadding: 0,
    loop: false,
    margin: 46,
    nav: true,
    navigation: true,
    dots: true,
    navText: false,
    responsive: {
        0: {
            items: 6,
            dots: false,
            navigation: false,
            margin: 0,
        },
        768: {
            items: 4,
            dots: true,
            navigation: false,
            margin: 30,
        },
        1200: {
            navText: [`<span class='d-flex align-items-center justify-content-center'>
<svg width="36" height="55" >
    <use xlink:href="../sprite.svg#prevOnlyArrow"></use>
    </svg></span>`, `<span class='d-flex align-items-center justify-content-center'>
<svg width="36" height="55" >
    <use xlink:href="../sprite.svg#nextOnlyArrow"></use>
    </svg>
</span>`],
            items: 5,
        }
    }
});

if(window.innerWidth<600){
$('.portfolio-container').owlCarousel({
    loop:false,
    margin:10,
    autoplay:false,
    nav:true,
    dots:true,
    navText: [`<span class='d-flex align-items-center justify-content-center arrow'>
<svg width="13" height="10" >
    <use xlink:href="../sprite.svg#prevArrow"></use>
    </svg></span>`, `<span class='d-flex align-items-center justify-content-center arrow'>
<svg width="17" height="10" >
    <use xlink:href="../sprite.svg#nextArrow"></use>
    </svg>
</span>`],
    responsive:{
        0:{
            items:1.5
        }
    }
});
}


const dots=document.querySelector('.portfolio-container  .owl-dots');
if(dots){
    let k=document.querySelector('.portfolio-container  .owl-nav').appendChild(dots)
}


$('.instructors-carousel').owlCarousel({
    stagePadding: 0,
    loop: false,
    margin: 45,
    nav: true,
    navigation: true,
    dots: false,
    navText: [`<span class='d-flex align-items-center justify-content-center'>
<svg>
    <use xlink:href="../sprite.svg#prevArrowLarge"></use>
    </svg></span>`, `<span class='d-flex align-items-center justify-content-center'>
<svg>
    <use xlink:href="../sprite.svg#nextArrowLarge"></use>
    </svg>
</span>`],
    responsive: {
        0: {
            items: 1.5,
        },
        600: {
            items: 2,
            margin: 20,
        },
        800: {
            items: 3,
            margin: 30,
        },
        1200: {

            items: 4,
        }
    }
});

