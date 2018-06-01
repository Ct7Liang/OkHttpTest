package ct7liang.android.apporitation.OkHttpUtils;

/**
 * Created by Administrator on 2018-06-01.
 */

public class Query {

    public static void query(final OnQuery onQuery){
        COkHttpUtils
                .post().url("http://bbs.52bqu.com/biz/userc/appGetUser")
                .execute(new COkHttpUtils.OnResponse() {
                    @Override
                    public void onSuccess(String s) {
                        onQuery.onQuerySuccess(s);
                    }

                    @Override
                    public void onError(Exception e) {
                        onQuery.onQueryError(e);
                    }
                });
    }

    public interface OnQuery{
        void onQuerySuccess(String s);
        void onQueryError(Exception e);
    }

}
