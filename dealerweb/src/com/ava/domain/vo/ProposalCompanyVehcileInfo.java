package com.ava.domain.vo;

public class ProposalCompanyVehcileInfo {
	

	private ProposalVO proposal = new ProposalVO();
		
	private VehicleVO vehicle = new VehicleVO();
	
	private CompanyVO company = new CompanyVO();
	
	private ApprovalVO approval = new ApprovalVO();
	
	private VehicleInstallationPlanVO vehicleInstallationPlan = new VehicleInstallationPlanVO();
	
	private BoxVO tbox = new BoxVO();
	
	private BoxOperationVO boxOperation = new BoxOperationVO();

	public ProposalVO getProposal() {
		return proposal;
	}

	public void setProposal(ProposalVO proposal) {
		this.proposal = proposal;
	}

	public VehicleVO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleVO vehicle) {
		this.vehicle = vehicle;
	}

	public CompanyVO getCompany() {
		return company;
	}

	public void setCompany(CompanyVO company) {
		this.company = company;
	}

	public BoxVO getTbox() {
		return tbox;
	}

	public void setTbox(BoxVO tbox) {
		this.tbox = tbox;
	}

	public ApprovalVO getApproval() {
		return approval;
	}

	public void setApproval(ApprovalVO approval) {
		this.approval = approval;
	}

	public BoxOperationVO getBoxOperation() {
		return boxOperation;
	}

	public void setBoxOperation(BoxOperationVO boxOperation) {
		this.boxOperation = boxOperation;
	}

	public VehicleInstallationPlanVO getVehicleInstallationPlan() {
		return vehicleInstallationPlan;
	}

	public void setVehicleInstallationPlan(
			VehicleInstallationPlanVO vehicleInstallationPlan) {
		this.vehicleInstallationPlan = vehicleInstallationPlan;
	}
   
}
