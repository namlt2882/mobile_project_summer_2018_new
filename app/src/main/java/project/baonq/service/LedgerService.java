package project.baonq.service;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import project.baonq.dao.LedgerDAO;
import project.baonq.enumeration.LedgerStatus;
import project.baonq.model.DaoSession;
import project.baonq.model.Ledger;
import project.baonq.model.LedgerDao;

public class LedgerService extends Service {


    public LedgerService(Application application) {
        super(application);
    }


    public Ledger findById(Long id) {
        return new LedgerDAO(application).findById(id);
    }

    public Long addLedger(String name, String currency, boolean isReport) {
        Ledger ledger = new Ledger();
        ledger.setName(name);
        ledger.setCurrency(currency);
        ledger.setCounted_on_report(isReport);
        ledger.setStatus(LedgerStatus.ENABLE.getStatus());
        long now = System.currentTimeMillis();
        ledger.setInsert_date(now);
        ledger.setLast_update(now);
        return new LedgerDAO(application).addLedger(ledger);
    }

    public void updateLedger(Ledger ledger) {
        Ledger origin = findById(ledger.getId());
        origin.setStatus(ledger.getStatus());
        origin.setCurrency(ledger.getCurrency());
        origin.setName(ledger.getName());
        origin.setCounted_on_report(ledger.getCounted_on_report());
        origin.setLast_update(System.currentTimeMillis());
        new LedgerDAO(application).updateLedger(ledger);
    }

    public List<Ledger> getAll() {
        return new LedgerDAO(application).getAll();
    }

    public Ledger findByServerId(Long id) {
        return new LedgerDAO(application).findByServerId(id);
    }

    public Long getLastUpdateTime() {
        SharedPreferences sharedPreferences = application.getSharedPreferences("sync", Context.MODE_PRIVATE);
        return sharedPreferences.getLong(LedgerSyncService.LEDGER_LASTUPDATE, Long.parseLong("0"));
    }

    public Long getLastUpdateTimeFromDb() {
        Ledger ledger = new LedgerDAO(application).findLastUpdateLedger();
        if (ledger != null) {
            return ledger.getLast_update();
        } else {
            return Long.parseLong("0");
        }
    }

    public void insertOrUpdate(List<Ledger> ledgers) {
        new LedgerDAO(application).insertOrUpdate(ledgers);
    }
}
