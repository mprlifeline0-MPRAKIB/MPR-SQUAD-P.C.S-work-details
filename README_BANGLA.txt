MPR SQUAD Android APK তৈরি করার সহজ নির্দেশনা
=============================================

এই ZIP-এর মধ্যে আপনার বর্তমান Offline HTML অ্যাপটি Android WebView প্রজেক্টে রাখা হয়েছে।
এটি এখনো APK নয়। GitHub Actions দিয়ে APK তৈরি হবে।

ধাপ ১: ZIP ফাইলটি ফোনে Extract করুন।
ধাপ ২: https://github.com এ গিয়ে Gmail দিয়ে Sign up / Sign in করুন।
ধাপ ৩: + চিহ্ন > New repository চাপুন।
       Repository name: MPR-SQUAD
       Public বা Private যেকোনোটি বেছে নিন, তারপর Create repository।
ধাপ ৪: নতুন Repository-তে Add file > Upload files চাপুন।
       Extract করা ফোল্ডারের ভেতরের সব ফাইল ও ফোল্ডার আপলোড করুন।
       বিশেষভাবে .github ফোল্ডারটিও আপলোড করতে হবে।
       Commit changes চাপুন।
ধাপ ৫: Repository-র Actions ট্যাব খুলুন।
       "Build MPR SQUAD APK" workflow নির্বাচন করে Run workflow চাপুন।
ধাপ ৬: Build শেষ হলে সবুজ Success দেখাবে। ওই রান খুলে নিচে Artifacts-এ
       MPR-SQUAD-debug-APK ডাউনলোড করুন।
ধাপ ৭: ZIP খুলে app-debug.apk বের করুন। APK-তে চাপ দিয়ে Install দিন।
       Android চাইলে Chrome/Files-এর জন্য "Install unknown apps" অনুমতি দিন।

অ্যাপের তথ্য:
- HTML ফাইল app/src/main/assets/index.html-এ আছে।
- অ্যাপের নাম MPR SQUAD।
- অ্যাপটি নেটওয়ার্ক পারমিশন নেয় না; অফলাইন WebView-তে চলে।
- হিসাব localStorage-এ থাকে; ফোন রিস্টার্টে সাধারণত মুছে যায় না।
- অ্যাপ আনইনস্টল বা App info > Storage > Clear data করলে হিসাব মুছে যেতে পারে।
- এটি Debug APK। নিজের ফোনে ইনস্টল করার জন্য উপযোগী; Play Store প্রকাশের জন্য
  আলাদা release signing প্রয়োজন।

সমস্যা হলে GitHub Actions-এর লাল Error অংশের স্ক্রিনশট পাঠান।
