#include "TableViewWidget.h"

TableViewWidget::TableViewWidget(TableModel* model, QWidget *parent)
	: QWidget(parent)
{
	ui.setupUi(this);
	this->dogsTableView = new QTableView{};
	QVBoxLayout* tableLay = new QVBoxLayout{ this };

	QSortFilterProxyModel* proxyModel = new QSortFilterProxyModel{};
	proxyModel->setSourceModel(model);
	proxyModel->setSortRole(Qt::UserRole);
	this->dogsTableView->setModel(proxyModel);
	this->dogsTableView->setSortingEnabled(true);

	tableLay->addWidget(this->dogsTableView);
}

TableViewWidget::~TableViewWidget()
{
}
