package com.example.vinaykl.bs2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class Healthy_Tips extends Activity {

    ViewPager viewPager;
    PagerAdapter adapter;
    String[] data;
    int[] flag;
    //String[] country;
    //String[] population;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy__tips);

        data = new String[]{"Curb your sweet tooth\n" +
                "Got a late-night sugar craving that just won't quit? \"To satisfy your sweet tooth without pushing yourself over the calorie edge, even in the late night hours, think 'fruit first,'\" says Jackie Newgent, RD, author of The Big Green Cookbook. So resist that chocolate cake siren, and instead enjoy a sliced apple with a tablespoon of nut butter (like peanut or almond) or fresh fig halves spread with ricotta. Then sleep sweet, knowing you're still on the right, healthy track.",
                "A workout buddy is hugely helpful for keeping motivated, but it's important to find someone who will inspire—not discourage. So make a list of all your exercise-loving friends, then see who fits this criteria, says Andrew Kastor, an ASICS running coach: Can your pal meet to exercise on a regular basis? Is she supportive (not disparaging) of your goals? And last, will your bud be able to keep up with you or even push your limits in key workouts? If you've got someone that fits all three, make that phone call.",
                 "Stock up on these\n" +
                         "While there are heaps of good-for-you foods out there, some key ingredients make it a lot easier to meet your weight-loss goals. Next grocery store run, be sure to place Newgent's top three diet-friendly items in your cart: balsamic vinegar (it adds a pop of low-cal flavor to veggies and salads), in-shell nuts (their protein and fiber keep you satiated), and fat-free plain yogurt (a creamy, comforting source of protein). \"Plus, Greek yogurt also works wonders as a natural low-calorie base for dressings and dips—or as a tangier alternative to sour cream,\" says Newgent. Talk about a multitasker!",
                "Relieve those achy muscles\n" +
                        "After a grueling workout, there's a good chance you're going to be feeling it (we're talking sore thighs, tight calves). Relieve post-fitness aches by submerging your lower body in a cold bath (50 to 55 degrees Fahrenheit; you may have to throw some ice cubes in to get it cold enough) for 10 to 15 minutes. \"Many top athletes use this trick to help reduce soreness after training sessions,\" says Andrew Kastor. And advice we love: \"An athlete training for an important race should consider getting one to two massages per month to help aid in training recovery,\" adds Kastor. Now that's speaking our language!",
                "Buy comfy sneaks\n" +
                        "You shouldn't buy kicks that hurt, bottom line! \"Your shoes should feel comfortable from the first step,\" says Andrew Kastor. So shop in the evening—your feet swell during the day and stop in the late afternoon, so you want to shop when they're at their biggest. Also make sure the sneaks are a little roomy—enough so that you can wiggle your toes, but no more than that. They should be comfy from the get-go, but Kastor says they'll be even more so once you have a good 20 to 40 miles on 'em.",
                "Pick your perfect tunes\n" +
                        "Running with music is a great way to get in a groove (just make sure it's not blasting too loudly, or you won't hear those cars!). To pick the ultimate iPod playlist, think about what gets you going. \"I know several elite athletes that listen to what we'd consider 'relaxing' music, such as symphony music, while they do a hard workout,\" says Andrew Kastor. So don't feel like you have to download Lady Gaga because her tunes are supposed to pump you up—go with any music that you find uplifting.",
                "When to weigh\n" +
                        "You've been following your diet for a whole week. Weigh to go! Now it's time to start tracking your progress (and make sure pesky pounds don't find their way back on). \"It's best to step on the scale in the morning before eating or drinking—and prior to plunging into your daily activities,\" says Newgent. For the most reliable number, be sure to check your poundage at a consistent time, whether daily or weekly.\n" +
                        "\n",
                "Police your portions\n" +
                        "Does your steak take up more than half your plate? Think about cutting your serving of beef in half. That's because it's best to try and fill half your plate with veggies or a mixture of veggies and fresh fruit, says Newgent, so that it's harder to overdo it on the more caloric dishes (like cheesy potatoes or barbecue sauce–slathered ribs—yum!).",
                "Combat cocktail hour\n" +
                        "Is it ladies' night? If you know you'll be imbibing more than one drink, feel (and sip!) right by always ordering water between cocktails, says Newgent. That way, you won't rack up sneaky liquid calories (and ruin your inhibition to resist those mozzarella sticks!). But your H20 doesn't have to be ho-hum. \"Make it festive by ordering the sparkling variety with plenty of fruit, like a lime, lemon, and orange wedge in a martini or highball glass,\" adds Newgent.",
                "Eat this, run that\n" +
                        "When you have a 5- or 10K (you get to eat more with a half or full marathon) on your calendar, it's important to plan out what you're going to eat the morning of the big day—something that will keep you fueled and also go down easy. While everyone is different, \"We always have good luck with a high-carbohydrate breakfast such as a small bowl of oatmeal with fruit or a couple of pieces of toast with peanut butter or cream cheese,\" says Andrew Kastor, who also advises eating around 200 to 250 (primarily carb) calories about 90 minutes before you warm up for your run . And don't worry about nixing your a.m. caffeine fix on race day. \"Coffee is great for athletic performances,\" Kastor adds, because it makes you sharper and may even give you extended energy. Talk about buzz-worthy!" };

        flag = new int[] { R.drawable.pic1, R.drawable.pic2,
                R.drawable.pic3, R.drawable.pic4,
                R.drawable.pic5, R.drawable.pic6, R.drawable.pic7,
                R.drawable.pic8, R.drawable.pic9, R.drawable.pic10};

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(Healthy_Tips.this, data,flag);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
    }
}
