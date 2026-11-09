MPR SQUAD P.C.S WORK DETAILS — SOURCE PROJECT v2.4

এই ZIP হলো আপডেট করা Android Studio/Gradle source project। এটি আগের MPR SQUAD অ্যাপেরই ধারাবাহিক সংস্করণ; নতুন করে বানানো আলাদা অ্যাপ নয়।

মূল স্ক্রিন ও হিসাব
- Home: Duty / Bill / Expense / Other; মাসিক ড্যাশবোর্ড ও Quick Search।
- Duty: তারিখ, ডিউটি/ডে-অফ, শুরু-শেষ সময়, স্থান; মাসিক ডিউটি দিন, ডে-অফ ও মোট ঘণ্টা।
- Bill: তারিখ/স্থান; যাতায়াতের যাওয়া ও আসার খরচ আলাদা; দিনের ও রাতের খাবার আলাদা; অন্যান্য খরচ; স্বয়ংক্রিয় মোট; Paid/Unpaid চিহ্ন।
- Expense: নাম ও ID দিয়ে ব্যক্তি ফোল্ডার; ব্যক্তিভিত্তিক খরচ, মাসিক মোট, ফোল্ডার নাম/ID Edit।
- প্রকৃত খরচের লেজার: প্রতিটি খরচে তারিখ/সময়, খরচের ধরন, যিনি টাকা দিয়েছেন, কার/কোন কাজের জন্য, পরিমাণ, স্থান, রুট, মন্তব্য। পরিবহন শুধু রিকশা, সিএনজি, মোটরসাইকেল, প্রাইভেট কার এবং অন্যান্য (নিজে নাম লিখুন)।
- প্রত্যেক ব্যক্তির বাস্তবে প্রদত্ত টাকার মোট স্বয়ংক্রিয়ভাবে যোগ হয়। সমান ভাগ, স্বয়ংক্রিয় দেনা বা ফেরত দেওয়ার হিসাব নেই।
- Other: নিজের ক্যাটাগরি/শিরোনাম ও বিবরণ যোগ করা যায়।
- বেতন / মোবাইল বিল / বোনাস রেকর্ড।

সম্পাদনা, রিপোর্ট ও সুরক্ষা
- Duty, Bill, Person Expense, প্রকৃত লেজার, Pay ও Custom record-এর Edit/Delete।
- Global Search; CSV-তে সব ধরনের রেকর্ড Export।
- তারিখ-সীমার Full Report Preview; Android Print service থেকে PDF Save; Gallery-তে রিপোর্টের ছবি সংরক্ষণ।
- ব্যক্তি ফোল্ডারের মাসিক PDF/Print ও CSV। রিপোর্টে MPR SQUAD হেডার ও প্রোফাইল ছবি থাকে।
- JSON Backup/Restore; Restore করার আগে বর্তমান ডেটার সুরক্ষা কপি ডাউনলোড।
- ৪–৮ সংখ্যার App PIN Lock। প্রোফাইল ছবি স্থায়ীভাবে Lock; অ্যাপ থেকে বদলানো/মুছা যাবে না।
- থিম: Maroon & Gold (নতুন ডিফল্ট), MPR Navy & Gold, Light Blue, Dark Mode, Green। থিম পছন্দ একই ডিভাইসে সংরক্ষিত থাকে।
- Android Back বোতাম স্ক্রিন/মেনু/সম্পাদনা অনুযায়ী কাজ করে; স্ক্রিনে প্রবেশ ও বের হওয়া—দুই দিকেই মসৃণ স্লাইড/ফেড অ্যানিমেশন আছে।
- Offline-first; ইন্টারনেট ছাড়াই হিসাব ও Local Storage ব্যবহার করা যায়।

ডেটা সামঞ্জস্য
- আগের Local Storage key অপরিবর্তিত: mpr_pcs_work_details_v1
- আগের travel/food amount নতুন split fields-এ migrate হয়; পুরোনো পরিমাণ হারানোর জন্য কোনো ইচ্ছাকৃত reset নেই।
- নতুন APK ইনস্টল করার আগে অবশ্যই অ্যাপের Backup থেকে JSON ফাইল ডাউনলোড করে রাখুন।
- অ্যাপ আনইনস্টল বা Storage Clear করলে Local Storage মুছে যেতে পারে। নিয়মিত Backup রাখুন।

APK Build
- GitHub Actions workflow: .github/workflows/build-apk.yml
- GitHub repository-তে main branch-এ push করুন অথবা Actions থেকে “Build MPR SQUAD APK” workflow Run workflow করুন।
- Artifact: MPR-SQUAD-debug-APK
- app/build.gradle-এ versionName 2.3 এবং versionCode 3।

সৎ যাচাই-নোট
- JavaScript syntax check এবং ZIP integrity check সফল হয়েছে। AndroidManifest XML parse-ও সফল।
- এই কাজের পরিবেশে Android SDK/Gradle toolchain নেই; তাই APK এখানে compile করা হয়নি এবং বাস্তব ফোনে পরীক্ষা করা হয়নি।
- GitHub Actions build সফল হওয়ার পর ফোনে ইনস্টল করে PDF Print/Save, Gallery Save, PIN Lock/Unlock, Backup/Restore, থিম সংরক্ষণ, Android Back navigation এবং পুরোনো ডেটা migration পরীক্ষা করতে হবে।
- App PIN সাধারণ app-level lock; এটি database encryption নয় এবং ফোনের screen lock-এর বিকল্প নয়।


v2.4 নোট
- অ্যাপের মূল ইন্টারফেসে Maroon & Gold থিম যুক্ত হয়েছে; নতুন/আগের ডিফল্ট থিম মারুন করা হয়েছে, তবে পরবর্তীতে ব্যবহারকারীর পছন্দ করা থিম সংরক্ষিত থাকবে।
- প্রতিটি স্ক্রিনে ঢোকার পাশাপাশি বের হওয়ার অ্যানিমেশনও যুক্ত হয়েছে।
- মূল MPR SQUAD লোগো ছবিটি আগের Navy/Gold আর্টওয়ার্কই থাকবে; থিম পরিবর্তনে অ্যাপের UI রঙ বদলাবে, ছবির ভেতরের রঙ নয়।
